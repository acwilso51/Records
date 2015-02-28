package Records.Attributes;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * The Class AlbumArtRetriever.
 *
 * @author Alexander
 */
public class AlbumArtRetriever {

  /**
   * Creates the directory.
   *
   * @param title
   *          the title
   * @param artist
   *          the artist
   * @param album
   *          the album
   * @return the string
   */
  public static String createDirectory(String title, String artist, String album) {
    String image;
    File dir = new File("C:\\Records\\Images\\" + artist + "\\" + album);
    BufferedImage img;
    if (dir.listFiles() != null) {
      image = "C:\\Records\\Images\\" + artist + "\\" + album + "\\" + album + ".jpg";
    } else {
      image = AlbumArtRetriever.retrieveArt(title, artist);
      if ("Unknown".equals(image) || image == null) {
        image = "C:\\Users\\Alexander\\MuRec\\no_img.gif";
      } else {
        try {
          img = ImageIO.read(new URL(image));
          if (img != null) {
            saveURLImage(image, album, artist);
            image = "C:\\Records\\Images\\" + artist + "\\" + album + "\\" + album + ".jpg";
          }
        } catch (IOException ex) {
          Logger.getLogger(AlbumArtRetriever.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
    return image;
  }

  /**
   * Art opt one.
   *
   * @param is the is
   * @return the string
   */
  private static String getLastFMAlbumArt(InputStream is) {
    System.out.println("AlbumArtRetriever.artOptOne(" + is.toString() + ")");
    String albArt = "";
    String Path = null;

    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    String line = "";
    try {
      while ((line = br.readLine()) != null) {
        System.out.println(line);
        Path = Path + line;
      }
      JSONObject objectInArray = null;
      System.out.println(Path.replaceFirst("null", ""));
      JSONObject outerObject = new JSONObject(Path.replaceFirst("null", "").trim()).getJSONObject(
          "track").getJSONObject("album");
      JSONArray songArray = outerObject.getJSONArray("image");
      objectInArray = songArray.getJSONObject(2);
      albArt = objectInArray.optString("#text");
    } catch (JSONException ex) {
      ex.printStackTrace();
      albArt = "";
    } catch (IOException e) {
      e.printStackTrace();
      albArt = "";
    }
    return albArt;
  }

  /**
   * Gets the last fm input stream.
   *
   * @param title the title
   * @param artist the artist
   * @return the last fm input stream
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private static InputStream getLastFMInputStream(String title, String artist) throws IOException {
    String path = "http://ws.audioscrobbler.com/2.0/?method=track.getInfo&api_key=b379fee6480a2478ba7aa5d3faa2699c&format=json&artist="
        + artist + "&track=" + title;
    System.out.println(path);
    URL url = new URL(path);
    return url.openStream();
  }

  /**
   * Retrieve art.
   *
   * @param title
   *          the title
   * @param artist
   *          the artist
   * @return the string
   */
  public static String retrieveArt(String title, String artist) {
    System.out.println("AlbumArtRetriever.AlbumArt(" + title + ", " + artist + ")");

    title = title.replaceAll(" ", "%20");
    artist = artist.replaceAll(" ", "%20");
    try {
      InputStream is = getLastFMInputStream(title, artist);
      if (is != null) {
        art = getLastFMAlbumArt(is);
        if ("".equals(art)) {
          art = "Unknown";
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return art;
  }

  /**
   * Save url image.
   *
   * @param imageUrl
   *          the image url
   * @param album
   *          the album
   * @param artist
   *          the artist
   */
  private static void saveURLImage(String imageUrl, String album, String artist) {
    try{  
    FileUtils.forceMkdir(new File("C:\\Records\\Images\\" + artist + "\\" + album));
      String destinationFile = "C:\\Records\\Images\\" + artist + "\\" + album + "\\" + album
          + ".jpg";
      URL url = new URL(imageUrl);
      OutputStream os;
      InputStream is = url.openStream();
        os = new FileOutputStream(destinationFile);
        byte[] b = new byte[2048];
        int length;
        while ((length = is.read(b)) != -1) {
          os.write(b, 0, length);
        }
        os.close();
      } catch (IOException ex) {
        Logger.getLogger(AlbumArtRetriever.class.getName()).log(Level.SEVERE, null, ex);
      }
  }

  /** The art. */
  private static String art = "";
}
