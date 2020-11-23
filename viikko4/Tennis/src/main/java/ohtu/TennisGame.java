package ohtu;

public class TennisGame {

    private int scorePlayer1 = 0;
    private int scorePlayer2 = 0;
    private final String player1Name;
    private final String player2Name;

    public TennisGame(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    public void wonPoint(String playerName) {
        if (playerName.equals(player1Name)) {
            scorePlayer1 += 1;
        } else {
            scorePlayer2 += 1;
        }
    }

    private String getScoreName(int score) {
        switch (score) {
            case 0:
                return "Love";
            case 1:
                return "Fifteen";
            case 2:
                return "Thirty";
            case 3:
                return "Forty";
            default:
                return "";
        }
    }

    private String getLeader() {
        if (scorePlayer1 > scorePlayer2) {
            return player1Name;
        } else if (scorePlayer2 > scorePlayer1) {
            return player2Name;
        }
        // no leader
        return "";
    }

    private String getGameSituation() {
        int resultDifference = Math.abs(scorePlayer1 - scorePlayer2);
        if (resultDifference == 0) {
            return "Deuce";
        } else if (resultDifference < 2) {
            return "Advantage " + getLeader();
        }
        return "Win for " + getLeader();
    }

    public String getScore() {
        // game has ended
        if (scorePlayer1 >= 4 || scorePlayer2 >= 4) {
            return getGameSituation();
        }
        // game is still going on
        // score is tied
        if (scorePlayer1 == scorePlayer2) {
            return getScoreName(scorePlayer1) + "-All";
        }
        return getScoreName(scorePlayer1) + "-" + getScoreName(scorePlayer2);
    }
}
