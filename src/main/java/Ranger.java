public class Ranger implements RangerDuties {
  private int id;
  private int badge_number;
  private String name;
  private String phone_number;

  public Ranger(int badge_number, String name, String phone_number) {
    this.id = id;
    this.badge_number = badge_number;
    this.name = name;
    this.phone_number = phone_number;
  }

  public String getName() {
    return this.name;
  }

  public int getBadgeNumber() {
    return this.badge_number;
  }

  public int getId() {
    return this.id;
  }

  public String getPhoneNumber() {
    return this.phone_number;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO rangers (badge_number, name, phone_number) VALUES (:badge_number, :name, :phone_number);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("badge_number", this.badge_number)
        .addParameter("name", this.name)
        .addParameter("phone_number", this.phone_number)
        .executeUpdate()
        .getKey();
    }
  }

  public void update() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE rangers SET (badge_number=:badge_number, name=:name, phone_number=:phone_number) WHERE id=:id;";
      con.createQuery(sql)
        .addParameter("id", id)
        .addParameter("badge_number", this.badge_number)
        .addParameter("name", this.name)
        .addParameter("phone_number", this.phone_number)
        .executeUpdate();
    }
  }

  public static List<Ranger> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM rangers;";
      return con.createQuery(sql)
        .executeAndFetch(Ranger.class);
    }
  }

  public static Ranger find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM rangers WHERE id=:id;";
      Ranger ranger = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Ranger.class);
      return ranger;
    }
  }


}
