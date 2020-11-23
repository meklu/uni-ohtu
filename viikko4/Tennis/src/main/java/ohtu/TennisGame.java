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

    private String getGameSituation() {
        int minusResult = scorePlayer1-scorePlayer2;
        if (minusResult==1) return "Advantage player1";
        else if (minusResult ==-1) return "Advantage player2";
        else if (minusResult >= 2) return "Win for player1";
        else return "Win for player2";
    }

    public String getScore() {
        // score is tied
        if (scorePlayer1 == scorePlayer2) {
            if (scorePlayer1 < 4) {
                return getScoreName(scorePlayer1) + "-All";
            } else {
                return "Deuce";
            }
        // game has ended
        } else if (scorePlayer1 >= 4 || scorePlayer2 >= 4) {
            return getGameSituation();
        }
        // game is still going on
        return getScoreName(scorePlayer1) + "-" + getScoreName(scorePlayer2);
    }
}
