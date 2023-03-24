import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
     * Collection of hashtags for the post
     */
    private List<String> hashtags;

    /**
     * Constructor to create the post
     * 
     * @param content Content of the post
     * @param numberOfLikes Number of likes for the post
     * @param date Date of the post
     * @param hashtags Collection of hashtags for the post
     */
    public Post(String content, int numberOfLikes, String date, List<String> hashtags) {
        this.content = content;
        this.numberOfLikes = numberOfLikes;
        try {
            this.date = dateFormat.parse(date);
        } catch (ParseException ex) {
            this.date = new Date();
        }
        this.hashtags = hashtags;
    }

    /**
     * Alternative constructor to create the post with the current date
     * 
     * @param content Content of the post
     * @param numberOfLikes Number of likes for the post
     * @param hashtags Collection of hashtags for the post
     */
    public Post(String content, int numberOfLikes, List<String> hashtags) {
        this.content = content;
        this.numberOfLikes = numberOfLikes;
        this.hashtags = hashtags;
        this.date = new Date();
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
     * Method to get the formatted date of the post
     * 
     * @return Formatted date of the post
     */
    public String getFormattedDate() {
        DateFormat formatter = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        return formatter.format(date);
    }

    /**
     * Method to get the collection of hashtags for the post
     * 
     * @return Collection of hashtags for the post
     */
    public List<String> getHashtags() {
        return hashtags;
    }

    /**
     * Method to add the like to the post
     */
    public void addLike() {
        this.numberOfLikes += 1;
    }
}
