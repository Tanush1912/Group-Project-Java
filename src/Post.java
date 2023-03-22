import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;

public class Post {
    /**
     * Content of the post
     */
    private String content;
    /**
     * Number of likes for the post
     */
    private int numberOfLikes;
    /**
     * Date of the post
     */
    private Date date;
    /**
     * Date format for the post
     */
    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    /**
     * Constructor to create the post
     * 
     * @param content Content of the post
     * @param numberOfLikes Number of likes for the post
     * @param date Date of the post
     */
    public Post(String content, int numberOfLikes, String date) {
        this.content = content;
        this.numberOfLikes = numberOfLikes;
        try {
            this.date = dateFormat.parse(date);
        } catch (ParseException ex) {
            this.date = new Date();
        }
    }

    /**
     * Method to get the content of the post
     * 
     * @return Content of the post
     */
    public String getContent() {
        return content;
    }

    /**
     * Method to get the number of likes for the post
     * 
     * @return Number of likes for the post
     */
    public int getNumberOfLikes() {
        return numberOfLikes;
    }

    /**
     * Method to get the date of the post
     * 
     * @return Date of the post
     */
    public Date getDate() {
        return date;
    }

    /**
     * Method to add the like to the post
     */
    public void addLike() {
        this.numberOfLikes += 1;
    }
}
