package ru.vadimio.server.gui;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.vadimio.server.core.SqlClient;

import javax.sql.DataSource;

@Configuration
//@ComponentScan(basePackages = "ru.vadimio.server")
public class SpringConfig {


    @Bean
    public ServerGUI serverGUI(){
        return new ServerGUI();
    }

    @Bean
    public SqlClient sqlClient(DataSource dataSource) {
        return new SqlClient(dataSource);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.sqlite.JDBC");
        ds.setUrl("jdbc:sqlite:lesson1_chat_server/src/main/java/ru/vadimio/server/chat.db");
        return ds;
    }

}
