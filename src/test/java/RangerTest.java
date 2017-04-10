import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class RangerTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Ranger_instantiatesCorrectly_false() {
    Ranger testRanger = new Ranger(12, "ranger", "555-555-5555");
    assertEquals(true, testRanger instanceof Ranger);
  }

  @Test
  public void getName_RangerInstantiatesWithName_Deer() {
    Ranger testRanger = new Ranger(12, "ranger", "555-555-5555");
    assertEquals("ranger", testRanger.getName());
  }

  @Test
  public void equals_returnsTrueIfNameIsTheSame_false() {
    Ranger firstRanger = new Ranger(12, "ranger", "555-555-5555");
    Ranger anotherRanger = new Ranger(12, "ranger", "555-555-5555");
    assertEquals(firstRanger.getName(), anotherRanger.getName());
  }

  @Test
  public void save_assignsIdToObjectAndSavesObjectToDatabase() {
    Ranger testRanger = new Ranger(12, "ranger", "555-555-5555");
    testRanger.save();
    Ranger savedRanger = Ranger.find(testRanger.getId());
    assertEquals(testRanger.getId(), savedRanger.getId());
  }

  @Test
  public void all_returnsAllInstancesOfRanger_false() {
    Ranger firstRanger = new Ranger(12, "ranger", "555-555-5555");
    firstRanger.save();
    Ranger secondRanger = new Ranger(12, "ranger", "555-555-5555");
    secondRanger.save();
    assertEquals(firstRanger.getId(), Ranger.find(firstRanger.getId()).getId());
    assertEquals(secondRanger.getId(), Ranger.find(secondRanger.getId()).getId());
  }

  @Test
  public void find_returnsRangerWithSameId_secondRanger() {
    Ranger firstRanger = new Ranger(12, "ranger", "555-555-5555");
    firstRanger.save();
    Ranger secondRanger = new Ranger(12, "ranger", "555-555-5555");
    secondRanger.save();
    assertEquals(Ranger.find(firstRanger.getId()).getName(), firstRanger.getName());
  }

  @Test
  public void find_returnsNullWhenNoRangerFound_null() {
    assertTrue(Ranger.find(999) == null);
  }

}
