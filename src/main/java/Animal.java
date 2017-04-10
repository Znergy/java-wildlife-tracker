import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;

public class Animal implements DatabaseManagement {
  public String name;
  public String color;
  public String picture;
  public int id;

  public Animal(String name, String color, String picture) {
    this.name = name;
    this.color = color;
    this.picture = picture;
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public String getColor() {
    return this.color;
  }

  public String getPicture() {
    return this.picture;
  }

  public int getId() {
    return this.id;
  }

  @Override
  public boolean equals(Object otherAnimal) {
    if(!(otherAnimal instanceof Animal)) {
      return false;
    } else {
      Animal newAnimal = (Animal) otherAnimal;
      return this.getName().equals(newAnimal.getName());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO animals (name, color, picture) VALUES (:name, :color, :picture);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("color", this.color)
        .addParameter("picture", this.picture)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Animal> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM animals;";
      return con.createQuery(sql)
        .executeAndFetch(Animal.class);
    }
  }

  public static Animal find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM animals WHERE id=:id;";
      Animal animal = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Animal.class);
      return animal;
    }
  }

  public void update() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE animals SET (name=:name, color=:color, picture=:picture) WHERE id=:id;";
      con.createQuery(sql)
        .addParameter("id", id)
        .addParameter("name", name)
        .addParameter("color", color)
        .addParameter("picture", picture)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM animals WHERE id=:id;";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public List<Sighting> getSightings() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM sightings WHERE animal_id=:id;";
        List<Sighting> sightings = con.createQuery(sql)
          .addParameter("id", id)
          .executeAndFetch(Sighting.class);
      return sightings;
    }
  }

}
