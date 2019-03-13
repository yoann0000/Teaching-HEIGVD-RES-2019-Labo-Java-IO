package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
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
    str.
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {

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
