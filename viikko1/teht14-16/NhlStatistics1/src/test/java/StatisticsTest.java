
import ohtuesimerkki.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class StatisticsTest {

    Reader readerStub = new Reader() {
        public List<Player> getPlayers() {
            ArrayList<Player> players = new ArrayList<>();

            players.add(new Player("Semenko", "EDM", 4, 12));
            players.add(new Player("Lemieux", "PIT", 45, 54));
            players.add(new Player("Kurri",   "EDM", 37, 53));
            players.add(new Player("Yzerman", "DET", 42, 56));
            players.add(new Player("Gretzky", "EDM", 35, 89));

            return players;
        }
    };

    Statistics stats;

    @Before
    public void setUp() {
        // luodaan Statistics-olio joka käyttää "stubia"
        stats = new Statistics(readerStub);
    }

    @Test
    public void teamPlayersFound() {
        assertEquals(3, stats.team("EDM").size());
    }

    @Test
    public void playerFound() {
        assertEquals(56, stats.search("Yzerman").getAssists());
    }

    @Test
    public void playerNotFound() {
        assertEquals(null, stats.search("Batman"));
    }

    @Test
    public void topScorersAreCorrect() {
        List<Player> l = stats.topScorers(2);
        assertNotEquals(null, l);
        assertEquals(2, l.size());
        assertEquals(true, l.get(0).getName().equals("Gretzky"));
        assertEquals(true, l.get(1).getName().equals("Lemieux"));
    }

}
