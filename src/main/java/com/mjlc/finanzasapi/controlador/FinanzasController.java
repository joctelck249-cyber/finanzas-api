package com.mjlc.finanzasapi.controlador;

import com.mjlc.finanzasapi.dto.LoginRequest;
import com.mjlc.finanzasapi.dto.ResumenMensualResponse;
import com.mjlc.finanzasapi.modelo.Categoria;
import com.mjlc.finanzasapi.modelo.ConfiguracionAnual;
import com.mjlc.finanzasapi.modelo.Movimiento;
import com.mjlc.finanzasapi.modelo.Usuario;
import com.mjlc.finanzasapi.repositorio.CategoriaRepository;
import com.mjlc.finanzasapi.repositorio.ConfiguracionAnualRepository;
import com.mjlc.finanzasapi.repositorio.MovimientoRepository;
import com.mjlc.finanzasapi.repositorio.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class FinanzasController {

    private final MovimientoRepository movimientoRepo;
    private final CategoriaRepository categoriaRepo;
    private final ConfiguracionAnualRepository configRepo;
    private final UsuarioRepository usuarioRepo;

    public FinanzasController(MovimientoRepository movimientoRepo,
                               CategoriaRepository categoriaRepo,
                               ConfiguracionAnualRepository configRepo,
                               UsuarioRepository usuarioRepo) {
        this.movimientoRepo = movimientoRepo;
        this.categoriaRepo = categoriaRepo;
        this.configRepo = configRepo;
        this.usuarioRepo = usuarioRepo;
    }

    @GetMapping("/saldo")
    public BigDecimal obtenerSaldo(@RequestParam Integer anio) {
        Optional<ConfiguracionAnual> config = configRepo.findById(anio);
        BigDecimal saldoInicial = config.map(ConfiguracionAnual::getSaldoInicial).orElse(BigDecimal.ZERO);
        BigDecimal movimientos = movimientoRepo.sumaMovimientos(anio);
        return saldoInicial.add(movimientos);
    }

    @GetMapping("/movimientos")
    public List<Movimiento> listarMovimientos(@RequestParam Integer anio) {
        return movimientoRepo.findByAnioOrderByFechaDesc(anio);
    }

    @PostMapping("/movimientos")
    public Movimiento guardarMovimiento(@RequestBody Movimiento movimiento) {
        return movimientoRepo.save(movimiento);
    }

    @GetMapping("/categorias")
    public List<Categoria> listarCategorias(@RequestParam String tipo) {
        return categoriaRepo.findByTipo(tipo);
    }

    @GetMapping("/resumen-mensual")
    public ResumenMensualResponse resumenMensual(@RequestParam Integer anio, @RequestParam Integer mes) {
        BigDecimal ingresos = movimientoRepo.sumaIngresosMes(anio, mes);
        BigDecimal egresos = movimientoRepo.sumaEgresosMes(anio, mes);
        return new ResumenMensualResponse(ingresos, egresos);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<Usuario> usuario = usuarioRepo.findByUsuarioAndPasswordHashAndActivo(
                request.getUsuario(), request.getPassword(), 1);

        if (usuario.isPresent()) {
            Usuario u = usuario.get();
            u.setPasswordHash(null);
            return ResponseEntity.ok(u);
        } else {
            return ResponseEntity.status(401).body("Usuario o contrasena incorrectos");
        }
    }
}