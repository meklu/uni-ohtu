// sample data
// {"name":"Janne Kuokkanen","nationality":"FIN","assists":0,"goals":0,"penalties":0,"team":"NJD","games":1}

package ohtu;

public class Player {
    private String name;
    private String nationality;
    private int assists;
    private int goals;
    private String team;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return name + " team " + team + " goals " + goals + " assists " + assists;
    }

}
