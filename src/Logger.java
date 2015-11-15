import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;

/**
 * @author hjorthjort
 *
 * Utility for writing markdown logs
 */
public class Logger {
    private PrintWriter writer;

    public Logger(String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        writer = new PrintWriter(fileName, "UTF-8");
    }

    public void writeParagraph(String text) {
        writer.println(text);
        writer.println();
    }

    public void writeBulletlist(String[] bullets) {
        for (String s : bullets) {
            writer.println("* " + s);
        }
        writer.println();
    }

    public void writeAnchor(String anchorName) {
        writer.println("<a name='" + anchorName + "'></a>");
    }

    public void writeHeader(String header, int level) {
        writer.println(new String(new char[level]).replace("\0", "#") + " " + header);
        writer.println();
    }

    public void writeTableMap(String keyHeader, String valueHeader, Map table) {
        writer.println("| " + keyHeader + " | " + valueHeader + " |");
        writer.println("|---|---|");
        for (Map.Entry entry : (Set<Map.Entry>) table.entrySet()) {
            writer.println("| " + entry.getKey().toString() + " | " + entry.getValue().toString() + " |");
        }
        writer.println();
    }


    public void close() {
        writer.close();
    }
}
