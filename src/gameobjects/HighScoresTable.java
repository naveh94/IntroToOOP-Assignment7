package gameobjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
/**
 * HighScoresTable object.
 * @author Naveh Marchoom
 *
 */
public class HighScoresTable implements Serializable {

    private List<ScoreInfo> scores;
    private int size;

    /**
     * Create an empty high-scores table with the specified size.
     * The size means that the table holds up to size top scores.
     * @param size int
     */
    public HighScoresTable(int size) {
        this.size = size;
        this.scores = new java.util.ArrayList<ScoreInfo>(size);
    }

    /**
     * Creates a new HighScoresTable object with size number 10.
     */
    public HighScoresTable() {
        this(10);
    }

    /**
     * Add a high-score.
     * @param score ScoreInfo
     */
    public void add(ScoreInfo score) {
        ScoreInfo found = null;
        for (ScoreInfo s : this.scores) {
            if (s.getName().equals(score.getName())) {
                if (s.getScore() >= score.getScore()) {
                    return;
                } else {
                    found = s;
                    break;
                }
            }
        }
        if (found != null) {
            scores.remove(found);
        }
        if (this.scores.size() == this.size) {
            if (this.scores.get(size - 1).getScore() < score.getScore()) {
                this.scores.remove(this.size - 1);
            } else {
                return;
            }
        }
        int i = 0;
        while (i < this.scores.size() && this.scores.get(i).getScore() >= score.getScore()) {
            i++;
        }
        if (i < this.size) {
            this.scores.add(i, score);
        }
    }

    /**
     * Return table size.
     * @return int
     */
    public int size() {
        return this.size;
    }

    /**
     * Return the current high scores.
     * The list is sorted such that the highest scores come first.
     * @return List<ScoreInfo>
     */
    public List<ScoreInfo> getHighScores() {
        return this.scores;
    }

    /**
     * return the rank of the current score: where will it be on the list if added.
     * Rank 1 means the score will be highest on the list.
     * Rank `size` means the score will be lowest.
     * Rank > `size` means the score is too low and will not be added to the list.
     * @param score int
     * @return int
     */
    public int getRank(int score) {
        return this.scores.indexOf(score);
    }

    /**
     * Clears the table.
     */
    public void clear() {
        this.scores.clear();
    }

    /**
     * Load table data from file. Current table data is cleared.
     * @param filename File
     * @throws IOException in case failed reading file.
     */
    public void load(File filename) throws IOException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
        try {
            HighScoresTable hst = (HighScoresTable) ois.readObject();
            this.size = hst.size();
            this.scores = hst.getHighScores();
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to find object in file: '" + filename.getPath() + "'.");
            e.printStackTrace();
        } finally {
            if (ois != null) {
                ois.close();
            }
        }
    }

    /**
     * Save table data to the specified file.
     * @param filename File
     * @throws IOException in case failed saving file.
     */
    public void save(File filename) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
        oos.writeObject(this);
        if (oos != null) {
            oos.close();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "HighScoresTable{" + "scores=" + scores + ", size=" + size + "}";
    }

    /**
     * Read a table from file and return it.
     * If the file does not exist, or there is a problem with reading it,
     * an empty table is returned.
     * @param filename File
     * @return HighScoresTable
     */
    public static HighScoresTable loadFromFile(File filename) {
        ObjectInputStream ois = null;
        HighScoresTable hst = new HighScoresTable();
        try {
            ois = new ObjectInputStream(new FileInputStream(filename));
            hst = (HighScoresTable) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File '" + filename.getPath() + "' was not found.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error reading the file.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Couldn't find class for object in file.");
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    System.out.println("Failed closing the file.");
                    e.printStackTrace();
                }
            }
        }
        return hst;
    }
}
