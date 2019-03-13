package ch.heigvd.res.labio.impl.filters;

import ch.heigvd.res.labio.impl.Utils;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {
  private int line;
  private String output;
  private char previousChar;

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  public FileNumberingFilterWriter(Writer out) {
    super(out);
    line = 1;
    output = line++ + "\t";
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    String[] strUtils = {"", str.substring(off, off+len)};

    if (output.length() > 2) {
      output = "";
    }

    do {
      strUtils = Utils.getNextLine(strUtils[1]);

      if (strUtils[0].isEmpty()){
        output = output.concat(strUtils[1]);
        break;
      } else {
        output = output.concat(strUtils[0]);
        output = output + line++ + "\t";
      }
    } while (!strUtils[1].isEmpty());
    out.write(output);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    out.write(Arrays.toString(cbuf));
  }

  @Override
  public void write(int c) throws IOException {
    if (previousChar == '\r' && (char) c != '\n') {
      output += line++ + "\t" + (char) c;
    } else if ((char) c != '\n') {
      output += (char) c;
    } else {
      output += "\n" + line++ + "\t";
    }

    out.write(output);
    output = "";
    previousChar = (char) c;
  }

}
