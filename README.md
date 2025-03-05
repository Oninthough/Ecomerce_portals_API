In spring mvc I have 2 module one is configurator another one will be used for common portal like infozone so that we can reduce number of controller service and repositories for large number of entities in Configurator, I have implemented this multimodule structure in spring boot I'm using community version of intellij ide give the steps and POM structure and the way to run it properly 
Project structure is
parent-project/
├── common-module/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── infozone/
│   │   │   │           └── common/
│   │   │   │               ├── util/
│   │   │   │               ├── exceptions/
│   │   │   │               ├── repository/
│   │   │   │               │   └── GenericRepository.java
│   │   │   │               └── service/
│   │   │   │                   └── GenericService.java
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
├── configurator-module/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── configurator/
│   │   │   │           ├── ConfiguratorApplication.java
│   │   │   │           ├── controller/
│   │   │   │           ├── service/
│   │   │   │           ├── repository/
│   │   │   │           └── entity/
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   └── pom.xml
└── pom.xml
now I'm having some issue regarding bean creation, so I will provide the codes I've written based on it please help me to fix the isse
parent pom
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.0</version>
    </parent>

    <groupId>com.configurator</groupId>
    <artifactId>parent-project</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>pom</packaging>

    <modules>
        <module>common-module</module>
        <module>configurator-module</module>
    </modules>

    <properties>
        <java.version>17</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <start-class>com.configurator.ConfiguratorApplication</start-class>
        <modelmapper.version>3.1.1</modelmapper.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>com.configurator</groupId>
                <artifactId>common-module</artifactId>
                <version>1.0-SNAPSHOT</version>
            </dependency>
            <dependency>
                <groupId>org.modelmapper</groupId>
                <artifactId>modelmapper</artifactId>
                <version>3.1.1</version>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <!-- Dependencies shared by all modules -->
    <dependencies>
        <!-- Spring Boot Starters -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>javax.persistence</groupId>
            <artifactId>javax.persistence-api</artifactId>
            <version>2.2</version>
        </dependency>
        <!-- Transaction -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-tx</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>jakarta.persistence</groupId>
            <artifactId>jakarta.persistence-api</artifactId>
            <version>3.1.0</version>
        </dependency>

        <!-- Development Tools -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>

        <!-- Database -->
        <dependency>
            <groupId>com.oracle.database.jdbc</groupId>
            <artifactId>ojdbc8</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!-- Utilities -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.modelmapper</groupId>
            <artifactId>modelmapper</artifactId>
        </dependency>
    </dependencies>
</project>
//Providing Configurator module first 
//pom
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>parent-project</artifactId>
        <groupId>com.configurator</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>configurator-module</artifactId>
    <dependencies>
        <dependency>
            <groupId>com.configurator</groupId>
            <artifactId>common-module</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <mainClass>com.configurator.ConfiguratorApplication</mainClass>
                    <layout>JAR</layout>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>

//entity
package com.configurator.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "USERNAME", schema = "CONFIGURATORUSER")
public class User {

    // Getters and Setters
    @Id
    @Column(name = "USERNAMEID")
    private Long userId;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "USERDOMAINID")
    private int domain;

    @Column(name = "PASSHINT")
    private String passint;

    @Column(name = "PASSWORD")
    private String password;

}

package com.configurator.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Table(name = "ORIG", schema = "TICSYSTEM")
public class Orig {
    @Id
    @Column(name = "ORIGID")
    private Long origId;

    @Column(name = "ORIGGRPID", nullable = false)
    private Long origGrpId;

    @Column(name = "NAME", nullable = false, length = 32)
    private String name;

    @Column(name = "DESCRIPTION", length = 256)
    private String description;

    @Column(name = "SERVICESTATUS", nullable = false)
    private Integer serviceStatus;

    @Column(name = "MAXCONNECTIONS")
    private Integer maxConnections;

    @Column(name = "LOCALSS7", length = 15)
    private String localSs7;

    @Column(name = "REMOTESS7", length = 15)
    private String remoteSs7;

    @Column(name = "LOCALIP", length = 24)
    private String localIp;

    @Column(name = "REMOTEIP", length = 24)
    private String remoteIp;

    @Column(name = "REMOTEMASK", length = 15)
    private String remoteMask;

    @Column(name = "USERNAMEID")
    private Long userNameId;

    @Column(name = "CREATETIMESTAMP")
    private LocalDateTime createTimestamp;

    @Column(name = "TUEID")
    private Long tueId;
}
//controller
package com.configurator.controller;

import com.configurator.requestResponse.*;
//import com.configurator.service.UserService;
import com.configurator.service.username.UserBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class  UserController {

    private final UserBaseService userBaseService;

    @Autowired
    public UserController(UserBaseService userBaseService) {
        this.userBaseService = userBaseService;
    }


    @GetMapping("/users/{username}")
    public ResponseEntity<ApiResponse<UserNameResponse>> getUserByUsername(
            @PathVariable String username) {
        UserNameResponse user = userBaseService.getUserByUsername(username);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "success", user));
    }

    @PutMapping("/users/{username}")
    public ResponseEntity<ApiResponse<UserNameResponse>> updateUsername(
            @PathVariable String username,
            @RequestBody UpdateUsernameRequest request) {
        UserNameResponse updatedUser = userBaseService.updateUsername(username, request.getNewUsername());
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Username updated successfully", updatedUser));
    }

    @GetMapping("/users/orig/{username}")
    public ResponseEntity<ApiResponse<List<OrigResponse>>> getOrigDetailsByUsername(
            @PathVariable String username) {
        List<OrigResponse> origDetails = userBaseService.getOrigDetailsByUsername(username);
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                "ORIG details retrieved successfully",
                origDetails
        ));
    }
}
//Repository
package com.configurator.repository;

import com.configurator.entity.Orig;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
//public interface OrigRepository extends JpaRepository<Orig, Long> {
//    List<Orig> findByUserNameId(Long userNameId);
//}

import com.infozone.common.repository.GenericRepository;

@Repository
public interface OrigRepository extends GenericRepository<Orig, Long> {
    default List<Orig> findByUserNameId(Long userNameId) {
        return findAllByField("userNameId", userNameId);
    }
}

package com.configurator.repository;

import com.configurator.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//@Repository
//public interface UserRepository extends JpaRepository<User, Long> {
//    Optional<User> findByUsername(String username);
//    Optional<User> findByDomain(String domain);
//}

import com.infozone.common.repository.GenericRepository;

@Repository
public interface UserRepository extends GenericRepository<User, Long> {
    default Optional<User> findByUsername(String username) {
        return findByField("username", username);
    }
}
//Config
package com.configurator.config;

import com.infozone.common.config.CommonJpaConfig;
import com.infozone.common.repository.GenericRepositoryImpl;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

//@Configuration
//@EnableJpaAuditing
//@EnableJpaRepositories(
//        basePackages = "com.configurator.repository",
//        repositoryBaseClass = GenericRepositoryImpl.class
//)
//public class ConfiguratorJpaConfig extends CommonJpaConfig {
//
//    @Bean
//    @Primary
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(dataSource);
//        em.setPackagesToScan("com.configurator.Entity");
//
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        em.setJpaVendorAdapter(vendorAdapter);
//        em.setJpaProperties(getJpaProperties());
//
//        return em;
//    }
//
//    @Bean
//    @Primary
//    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
//        return transactionManager;
//    }
//
//    @Override
//    protected Properties getJpaProperties() {
//        Properties properties = super.getJpaProperties();
//        // Add any configurator-specific properties here if needed
//        return properties;
//    }
//}



@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(
        basePackages = "com.configurator.repository",
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager",
        repositoryBaseClass = GenericRepositoryImpl.class
)
public class ConfiguratorJpaConfig extends CommonJpaConfig {

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPersistenceUnitName("configuratorPU");
        em.setPackagesToScan("com.configurator.entity");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setShowSql(true);
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.Oracle12cDialect");
        em.setJpaVendorAdapter(vendorAdapter);

        Properties properties = getJpaProperties();
        properties.put("hibernate.default_schema", "CONFIGURATORUSER");
        properties.put("hibernate.physical_naming_strategy",
                "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        em.setJpaProperties(properties);

        return em;
    }

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
        return transactionManager;
    }

    @Override
    protected Properties getJpaProperties() {
        Properties properties = super.getJpaProperties();
        properties.setProperty("hibernate.physical_naming_strategy",
                "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        return properties;
    }
}
//service
package com.configurator.service.username;

import com.configurator.entity.User;
import com.configurator.requestResponse.OrigResponse;
import com.configurator.requestResponse.UserNameResponse;
import com.infozone.common.service.GenericCrudService;

import java.util.List;

public interface UserBaseService extends GenericCrudService<User, Long, UserNameResponse> {
    UserNameResponse getUserByUsername(String username);
    UserNameResponse updateUsername(String currentUsername, String newUsername);
    List<OrigResponse> getOrigDetailsByUsername(String username);
    UserNameResponse convertToResponse(User user);

}

/////
package com.configurator.service.username;

import com.configurator.entity.Orig;
import com.configurator.entity.User;
import com.configurator.repository.OrigRepository;
import com.configurator.repository.UserRepository;
import com.configurator.requestResponse.OrigResponse;
import com.configurator.requestResponse.UserNameResponse;
import com.infozone.common.exceptions.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import com.infozone.common.service.impl.GenericCrudServiceImpl;

import javax.transaction.Transactional;

@Slf4j
@Transactional
public abstract class AbstractUserServiceImpl extends GenericCrudServiceImpl<User, Long, UserNameResponse> implements UserBaseService {
    private final OrigRepository origRepository;
    private final UserRepository userRepository;

    public AbstractUserServiceImpl(UserRepository userRepository, OrigRepository origRepository) {
        super(userRepository);
        this.userRepository =  userRepository;
        this.origRepository = origRepository;
    }

    @Override
    public UserNameResponse convertToResponse(User user) {
        UserNameResponse response = new UserNameResponse();
        response.setUserId(user.getUserId());
        response.setUsername(user.getUsername());
        response.setDomain(user.getDomain());
        response.setPassint(user.getPassint());
        response.setPassword(user.getPassword());
        response.setDomainName(user.getDomain() == 1 ? "Targus" : "Other");
        return response;
    }

    @Override
    public UserNameResponse getUserByUsername(String username) {
        log.info("Getting user by username: {}", username);
        ZonedDateTime startTime = ZonedDateTime.now(ZoneOffset.UTC);

        try {
            User user = (userRepository.findByUsername(username)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username)));
            return convertToResponse(user);
        } catch (Exception e) {
            handleException("getUserByUsername", e);
            throw e;
        } finally {
            logMethodCompletionTime("getUserByUsername", startTime);
        }
    }

    @Override
    public UserNameResponse updateUsername(String currentUsername, String newUsername) {
        log.info("Updating username from {} to {}", currentUsername, newUsername);
        ZonedDateTime startTime = ZonedDateTime.now(ZoneOffset.UTC);

        try {
            User user = userRepository.findByUsername(currentUsername)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + currentUsername));

            if (userRepository.findByUsername(newUsername).isPresent()) {
                throw new IllegalArgumentException("Username already exists: " + newUsername);
            }

            user.setUsername(newUsername);
//            public save(user);
            return save(user);
        } catch (Exception e) {
            handleException("updateUsername", e);
            throw e;
        } finally {
            logMethodCompletionTime("updateUsername", startTime);
        }
    }

    @Override
    public List<OrigResponse> getOrigDetailsByUsername(String username) {
        log.info("Getting ORIG details for username: {}", username);
        ZonedDateTime startTime = ZonedDateTime.now(ZoneOffset.UTC);

        try {
            User user = (userRepository.findByUsername(username)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username)));

            List<Orig> origList = origRepository.findByUserNameId(user.getUserId());
            if (origList.isEmpty()) {
                throw new EmptyResultDataAccessException("No ORIG details found for user: " + username, 1);
            }

            return origList.stream()
                    .map(this::convertToOrigResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            handleException("getOrigDetailsByUsername", e);
            throw e;
        } finally {
            logMethodCompletionTime("getOrigDetailsByUsername", startTime);
        }
    }

    private OrigResponse convertToOrigResponse(Orig orig) {
        OrigResponse response = new OrigResponse();
        response.setOrigId(orig.getOrigId());
        response.setOrigGrpId(orig.getOrigGrpId());
        response.setName(orig.getName());
        response.setDescription(orig.getDescription());
        response.setServiceStatus(orig.getServiceStatus());
        response.setMaxConnections(orig.getMaxConnections());
        response.setLocalSs7(orig.getLocalSs7());
        response.setRemoteSs7(orig.getRemoteSs7());
        response.setLocalIp(orig.getLocalIp());
        response.setRemoteIp(orig.getRemoteIp());
        response.setRemoteMask(orig.getRemoteMask());
        response.setUserNameId(orig.getUserNameId());
        response.setCreateTimestamp(orig.getCreateTimestamp());
        response.setTueId(orig.getTueId());
        return response;
    }
}
////
package com.configurator.service.username;
import com.configurator.repository.OrigRepository;
import com.configurator.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Service
@Transactional
//@RequiredArgsConstructor
public class UserServiceImpl extends AbstractUserServiceImpl {

    public UserServiceImpl(UserRepository userRepository, OrigRepository origRepository) {
        super(userRepository, origRepository);
    }

    @Override
    public void deleteById(Long aLong) {

    }
}
//this is the main class for the configuration application 
@SpringBootApplication
@ComponentScan(basePackages = {"com.configurator", "com.infozone.common"})
@EntityScan(basePackages = "com.configurator.entity")  // Update package name
public class ConfiguratorApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfiguratorApplication.class, args);
    }
}
//response classes
package com.configurator.requestResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNameResponse {
    private Long userId;
    private String username;
    private int domain;
    private String domainName;
    private String passint;
    private String password;
}
@Getter
@Setter
public class UpdateUsernameRequest {
    private String newUsername;
}
@Getter
@Setter
public class OrigResponse {
    private Long origId;
    private Long origGrpId;
    private String name;
    private String description;
    private Integer serviceStatus;
    private Integer maxConnections;
    private String localSs7;
    private String remoteSs7;
    private String localIp;
    private String remoteIp;
    private String remoteMask;
    private Long userNameId;
    private LocalDateTime createTimestamp;
    private Long tueId;
}////
applicatiion.prop
spring.datasource.url=jdbc:oracle:thin:@ch03-eid-dev-db-oracle03.neustar.net:2115/nisdev.neustar.net?user=ConfiguratorUser&password=Configurator1
server.port=9095
spring.jpa.show-sql=true
spring.hibernate.ddl-auto=update
spring.datasource.driverClassName=oracle.jdbc.OracleDriver
#spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.Oracle12cDialect
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl

#cors properties
cors.allowedOrigins=http://localhost:3000
cors.pathPattern=/api/v1/**
cors.allowedMethods=GET,POST,PUT,PATCH,DELETE
cors.allowedHeaders=Host,User-Agent,Authorization,Content-Type,Content-Length
cors.allowCredentials=true
cors.maxAge=3600

//Common Module
//config
package com.infozone.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class CommonJpaConfig {

    protected Properties getJpaProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.OracleDialect");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        return properties;
    }
}
package com.infozone.common.config;

import com.infozone.common.repository.GenericRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = {"com.infozone.common.repository"},
        repositoryBaseClass = GenericRepositoryImpl.class
)
public class GenericRepositoryConfig {
}
package com.infozone.common.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }
}
//Repository
package com.infozone.common.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import java.util.List;

@NoRepositoryBean
public interface GenericRepository<T, ID> extends JpaRepository<T, ID> {
    Optional<T> findByField(String fieldName, Object value);
    List<T> findAllByField(String fieldName, Object value);
    Page<T> findAllByField(String fieldName, Object value, Pageable pageable);
    boolean existsByField(String fieldName, Object value);
}
package com.infozone.common.repository;

import javax.persistence.EntityManager;
import org.springframework.stereotype.Component;

@Component
public class GenericRepositoryFactory {
    private final EntityManager entityManager;

    public GenericRepositoryFactory(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public <T, ID> GenericRepository<T, ID> createRepository(Class<T> domainClass) {
        return new GenericRepositoryImpl<>(domainClass, (javax.persistence.EntityManager) entityManager);
    }
}
package com.infozone.common.repository;

import com.infozone.common.repository.GenericRepository;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.persistence.criteria.CriteriaBuilder;
//import jakarta.persistence.criteria.CriteriaQuery;
//import jakarta.persistence.criteria.Root;

// Remove any jakarta.persistence imports
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Transactional(readOnly = true)
public class GenericRepositoryImpl<T, ID> implements GenericRepository<T, ID> {

    @PersistenceContext
    protected EntityManager entityManager;

    protected final Class<T> domainClass;
    protected final JpaEntityInformation<T, ID> entityInformation;
    protected final SimpleJpaRepository<T, ID> delegate;

    public GenericRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        this.domainClass = domainClass;
        this.entityManager = entityManager;
        this.entityInformation = (JpaEntityInformation<T, ID>) JpaEntityInformationSupport.getEntityInformation(domainClass, entityManager);
        this.delegate = new SimpleJpaRepository<>(this.entityInformation, entityManager);
    }

    @Override
    public Optional<T> findByField(String fieldName, Object value) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> query = cb.createQuery(domainClass);
            Root<T> root = query.from(domainClass);

            query.select(root)
                    .where(cb.equal(root.get(fieldName), value));

            return Optional.of(entityManager.createQuery(query)
                    .setMaxResults(1)
                    .getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public List<T> findAllByField(String fieldName, Object value) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(domainClass);
        Root<T> root = query.from(domainClass);

        query.select(root)
                .where(cb.equal(root.get(fieldName), value));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Page<T> findAllByField(String fieldName, Object value, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(domainClass);
        Root<T> root = query.from(domainClass);

        query.select(root)
                .where(cb.equal(root.get(fieldName), value));

        List<T> results = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        // Count query for pagination
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<T> countRoot = countQuery.from(domainClass);
        countQuery.select(cb.count(countRoot))
                .where(cb.equal(countRoot.get(fieldName), value));

        Long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public boolean existsByField(String fieldName, Object value) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<T> root = query.from(domainClass);

        query.select(cb.count(root))
                .where(cb.equal(root.get(fieldName), value));

        return entityManager.createQuery(query).getSingleResult() > 0;
    }

    // Delegate methods from JpaRepository
    @Override
    public List<T> findAll() {
        return delegate.findAll();
    }

    @Override
    public List<T> findAll(Sort sort) {
        return delegate.findAll(sort);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return delegate.findAll(pageable);
    }

    @Override
    public List<T> findAllById(Iterable<ID> ids) {
        return delegate.findAllById(ids);
    }

    @Override
    public long count() {
        return delegate.count();
    }

    @Override
    public void deleteById(ID id) {
        delegate.deleteById(id);
    }

    @Override
    public void delete(T entity) {
        delegate.delete(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends ID> ids) {
        delegate.deleteAllById(ids);
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        delegate.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        delegate.deleteAll();
    }

    @Override
    @Transactional
    public <S extends T> S save(S entity) {
        return delegate.save(entity);
    }

    @Override
    @Transactional
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        return delegate.saveAll(entities);
    }

    @Override
    public Optional<T> findById(ID id) {
        return delegate.findById(id);
    }

    @Override
    public boolean existsById(ID id) {
        return delegate.existsById(id);
    }

    @Override
    public void flush() {
        delegate.flush();
    }

    @Override
    @Transactional
    public <S extends T> S saveAndFlush(S entity) {
        return delegate.saveAndFlush(entity);
    }

    @Override
    @Transactional
    public <S extends T> List<S> saveAllAndFlush(Iterable<S> entities) {
        return delegate.saveAllAndFlush(entities);
    }

    @Override
    @Transactional
    public void deleteAllInBatch(Iterable<T> entities) {
        delegate.deleteAllInBatch(entities);
    }

    @Override
    @Transactional
    public void deleteAllByIdInBatch(Iterable<ID> ids) {
        delegate.deleteAllByIdInBatch(ids);
    }

    @Override
    @Transactional
    public void deleteAllInBatch() {
        delegate.deleteAllInBatch();
    }

    @Override
    public T getOne(ID id) {
        return delegate.getOne(id);
    }

    @Override
    public T getById(ID id) {
        return delegate.getById(id);
    }

    @Override
    public T getReferenceById(ID id) {
        return delegate.getReferenceById(id);
    }

    @Override
    public <S extends T> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends T> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends T> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends T, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
//service
package com.infozone.common.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GenericCrudService<T, ID, R> {
    R save(T entity);
    Optional<R> findById(ID id);
    List<R> findAll();
    Page<R> findAll(Pageable pageable);
    void deleteById(ID id);
    long getRecordCount();
}

package com.infozone.common.service.impl;
import com.infozone.common.service.GenericCrudService;
import com.infozone.common.exceptions.DatabaseAccessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.dao.DataAccessException;

import java.time.Duration;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public abstract class GenericCrudServiceImpl<T, ID, R> implements GenericCrudService<T, ID, R> {

    private static final String DB_ERROR = "Database access error occurred";
    protected final JpaRepository<T, ID> repository;

    protected GenericCrudServiceImpl(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public R save(T entity) {
        ZonedDateTime startTime = ZonedDateTime.now(ZoneOffset.UTC);
        try {
            T savedEntity = repository.save(entity);
            return convertToResponse(savedEntity);
        } catch (DataAccessException e) {
            log.error(DB_ERROR, e);
            throw new DatabaseAccessException(DB_ERROR);
        } catch (Exception e) {
            handleException("save", e);
            throw e;
        } finally {
            logMethodCompletionTime("save", startTime);
        }
    }

    @Override
    public Optional<R> findById(ID id) {
        ZonedDateTime startTime = ZonedDateTime.now(ZoneOffset.UTC);
        try {
            return repository.findById(id).map(this::convertToResponse);
        } catch (DataAccessException e) {
            log.error(DB_ERROR, e);
            throw new DatabaseAccessException(DB_ERROR);
        } catch (Exception e) {
            handleException("findById", e);
            throw e;
        } finally {
            logMethodCompletionTime("findById", startTime);
        }
    }

    @Override
    public List<R> findAll() {
        ZonedDateTime startTime = ZonedDateTime.now(ZoneOffset.UTC);
        try {
            return repository.findAll().stream()
                    .map(this::convertToResponse)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            log.error(DB_ERROR, e);
            throw new DatabaseAccessException(DB_ERROR);
        } catch (Exception e) {
            handleException("findAll", e);
            throw e;
        } finally {
            logMethodCompletionTime("findAll", startTime);
        }
    }

    @Override
    public Page<R> findAll(Pageable pageable) {
        ZonedDateTime startTime = ZonedDateTime.now(ZoneOffset.UTC);
        try {
            return repository.findAll(pageable).map(this::convertToResponse);
        } catch (DataAccessException e) {
            log.error(DB_ERROR, e);
            throw new DatabaseAccessException(DB_ERROR);
        } catch (Exception e) {
            handleException("findAll", e);
            throw e;
        } finally {
            logMethodCompletionTime("findAll", startTime);
        }
    }

    @Override
    public long getRecordCount() {
        try {
            return repository.count();
        } catch (DataAccessException e) {
            log.error(DB_ERROR, e);
            throw new DatabaseAccessException(DB_ERROR);
        }
    }

    protected abstract R convertToResponse(T entity);

    protected void handleException(String methodName, Exception e) {
        log.error("Error in {}.{}: {}", getClass().getSimpleName(), methodName, e.getMessage(), e);
    }

    protected void logMethodCompletionTime(String methodName, ZonedDateTime startTime) {
        ZonedDateTime endTime = ZonedDateTime.now(ZoneOffset.UTC);
        Duration duration = Duration.between(startTime, endTime);
        log.info("{}.{} | Completed in {} milliseconds",
                getClass().getSimpleName(), methodName, duration.toMillis());
    }
}
//Utill
package com.infozone.common.util;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.ZonedDateTime;

@Slf4j
public class LoggingUtils {

    public static void logMethodCompletionTime(String className, String methodName, ZonedDateTime startTime, ZonedDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        log.info("{}.{} | Completed in {} milliseconds", className, methodName, duration.toMillis());
    }

    public static void handleException(String className, String methodName, Exception e) {
        log.error("Error in {}.{}: {}", className, methodName, e.getMessage(), e);
    }
}

//Resources/META-INF
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.2"
             xmlns="http://xmlns.jcp.org/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence
                                http://xmlns.jcp.org/xml/ns/persistence/persistence_2_2.xsd">
    <persistence-unit name="configuratorPU" transaction-type="RESOURCE_LOCAL">
        <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>

        <!-- Explicitly list your entity classes -->
        <class>com.configurator.entity.User</class>

        <exclude-unlisted-classes>false</exclude-unlisted-classes>

        <properties>
            <!-- Database properties -->
            <property name="hibernate.dialect" value="org.hibernate.dialect.OracleDialect"/>
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.format_sql" value="true"/>

            <!-- Entity scanning -->
            <property name="hibernate.archive.autodetection" value="class"/>

            <!-- Schema configuration -->
            <property name="hibernate.default_schema" value="CONFIGURATORUSER"/>

            <!-- Disable JTA -->
            <property name="hibernate.transaction.coordinator_class" value="jdbc"/>
        </properties>
    </persistence-unit>
</persistence>

and I'm getting 
org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'userController' defined in file [/Users/anghosh/Desktop/work5/SP25-4/BE/Infozone/Confi/parent-project/configurator-module/target/classes/com/configurator/controller/UserController.class]: Unsatisfied dependency expressed through constructor parameter 0; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'userServiceImpl' defined in file [/Users/anghosh/Desktop/work5/SP25-4/BE/Infozone/Confi/parent-project/configurator-module/target/classes/com/configurator/service/username/UserServiceImpl.class]: Unsatisfied dependency expressed through constructor parameter 0; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'userRepository' defined in com.configurator.repository.UserRepository defined in @EnableJpaRepositories declared on ConfiguratorJpaConfig: Invocation of init method failed; nested exception is java.lang.IllegalArgumentException: Not a managed type: class com.configurator.entity.User
how to fix this if there is anything releted to code or  dependancy also please fix that


