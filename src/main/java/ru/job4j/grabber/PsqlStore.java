package ru.job4j.grabber;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {

    private static final Logger LOG = LogManager.getLogger(PsqlStore.class.getName());
    private final Connection connection;

    public PsqlStore(Properties config) {
        try {
            Class.forName(config.getProperty("jdbc.driver"));
            this.connection = DriverManager.getConnection(
                    config.getProperty("jdbc.url"),
                    config.getProperty("jdbc.username"),
                    config.getProperty("jdbc.password"));
        } catch (Exception ex) {
            LOG.error("Exception ", ex);
            throw new IllegalStateException(ex);
        }
    }

    @Override
    public void save(Post post) {
        var request = "INSERT INTO post(name, text, on conflict (link) do nothing, created) VALUES(?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setString(1, post.getTitle());
            statement.setString(2, post.getDescription());
            statement.setString(3, post.getLink());
            statement.setTimestamp(4, Timestamp.valueOf(post.getCreated()));
            statement.execute();
        } catch (SQLException ex) {
            LOG.error("Exception ", ex);
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM post;")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    posts.add(getPost(resultSet));
                }
            }
        } catch (Exception ex) {
            LOG.error("Exception ", ex);
        }
        return posts;
    }

    @Override
    public Post findById(int id) {
        Post post = null;
        try (PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM post WHERE id = ?;"
                     )) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    post = getPost(resultSet);
                }
            }
        } catch (Exception ex) {
            LOG.error("Exception ", ex);
        }
        return post;
    }

    private Post getPost(ResultSet resultSet) throws SQLException {
        return new Post(resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("link"),
                resultSet.getString("text"),
                resultSet.getTimestamp("created").toLocalDateTime());
    }

    @Override
    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            LOG.error("Exception ", ex);
        }
    }

    public static void main(String[] args) {
        Properties config = new Properties();
        try (InputStream in = PsqlStore.class
                .getClassLoader().getResourceAsStream("psqlStore.properties")) {
            config.load(in);
        } catch (IOException ex) {
            LOG.error("Exception ", ex);
            throw new IllegalArgumentException(ex);
        }
        try (PsqlStore psqlStore = new PsqlStore(config)) {
            psqlStore.save(new Post("vacancy1", "http:vacancy1",
                    "description vacancy1", LocalDateTime.now()));
            psqlStore.save(new Post("vacancy2", "http:vacancy2",
                    "description vacancy2", LocalDateTime.now()));
            psqlStore.save(new Post("vacancy3", "http:vacancy3",
                    "description vacancy3", LocalDateTime.now()));
            System.out.println("Элементы в базе:");
            psqlStore.getAll().forEach(System.out::println);
            System.out.println("Элемент по второму индексу:");
            System.out.println(psqlStore.findById(2));
        }
    }
}
