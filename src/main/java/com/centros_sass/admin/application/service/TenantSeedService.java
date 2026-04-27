package com.centros_sass.admin.application.service;

import com.centros_sass.app.model.catalogs.organization.Role;
import com.centros_sass.app.model.profiles.workers.Worker;
import com.centros_sass.app.repository.catalogs.organization.RoleRepository;
import com.centros_sass.app.repository.profiles.WorkerRepository;
import com.centros_sass.core.tenant.TenantContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class TenantSeedService {

    private static final Pattern SLUG_PATTERN = Pattern.compile("^[a-z0-9-]+$");

    private final WorkerRepository workerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final DataSource dataSource;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Worker seedDirector(
            String slug, String email, String password, String firstName,
            String firstSurname, String dni, String phone) {

        log.info("SEED-STEP 1: Iniciando sembrado para tenant '{}'", slug);
        log.debug("SEED-STEP 2: TenantContext actual: '{}'", TenantContext.get());

        // Verificación extra vía JDBC para debuggear en consola
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SHOW search_path")) {
            if (rs.next()) {
                log.info("SEED-DEBUG-SQL: Search_path real en DB ahora mismo: {}", rs.getString(1));
            }
        } catch (SQLException e) {
            log.error("SEED-DEBUG-SQL: No se pudo verificar el search_path");
        }

        try {
            log.info("SEED-STEP 3: Buscando ROLE_DIRECTOR en el repositorio...");
            Role directorRole = roleRepository.findByRoleName("ROLE_DIRECTOR")
                    .orElseThrow(() -> new IllegalStateException(
                            "ERROR: ROLE_DIRECTOR no encontrado en el schema del tenant " + slug));

            log.info("SEED-STEP 4: Creando entidad Worker");
            Worker director = new Worker();
            director.setTenantId(slug);
            director.setFirstName(firstName);
            director.setFirstSurname(firstSurname);
            director.setDni(dni);
            director.setMainPhone(phone);
            director.setEmail(email);
            director.setPassword(passwordEncoder.encode(password));
            director.setIsActive(true);

            director.getRoles().add(directorRole);

            log.info("SEED-STEP 5: Guardando director...");
            director = workerRepository.save(director);
            log.info("SEED-STEP 6: Director creado con éxito. ID: {}", director.getId());

            return director;
        } catch (Exception e) {
            log.error("SEED-ERROR: Fallo fatal en el sembrado: {}", e.getMessage(), e);
            throw e;
        }
    }
}