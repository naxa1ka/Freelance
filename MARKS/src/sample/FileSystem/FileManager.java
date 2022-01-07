package sample.FileSystem;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import javafx.scene.control.ContextMenu;
import javafx.stage.FileChooser;
import jdk.nashorn.internal.runtime.Specialization;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import sample.DB.SqliteHandler;
import sample.Model.Beans.*;
import sample.Model.Beans.Collection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileManager {
    private static final SqliteHandler db = SqliteHandler.getInstance();


    public static String getLogFilePath() {
        Logger logger = Logger.getRootLogger();
        FileAppender fileAppender = (FileAppender) logger.getAllAppenders().nextElement();
        return fileAppender.getFile();
    }

    public static void openLogFile() throws IOException {
        Desktop.getDesktop().open(new File(getLogFilePath()));
    }

    public static File fileOpening() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        return chooser.showOpenDialog(new ContextMenu());
    }

    private static File fileOpenSaving(String extension) {
        FileChooser fileChooser = new FileChooser();

        // Задаём фильтр расширений
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Files (*" + extension + ")", "*" + extension);
        fileChooser.getExtensionFilters().add(extFilter);

        // Показываем диалог сохранения файла
        File file = fileChooser.showSaveDialog(new ContextMenu());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(extension)) {
                file = new File(file.getPath() + extension);
            }
        }
        return file;
    }

    public static File txtChooser() {
        return fileOpenSaving(".txt");
    }

    public static File xmlChooser() {
        return fileOpenSaving(".xml");
    }

    public static File htmlChooser() {
        return fileOpenSaving(".html");
    }

    public static File pdfChooser() {
        return fileOpenSaving(".pdf");
    }

    public static void saveHTML(File file, Collection collection) {
        String begin =
                "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML  4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\n" +
                        "<html>\n" +
                        " <head>\n" +
                        "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                        "  <title>Тег TABLE</title>\n" +
                        " </head>\n" +
                        " <body>\n" +
                        "  <table border=\"1\" width=\"100%\" cellpadding=\"5\">";
        String end =
                " </table>\n" +
                        " </body>\n" +
                        "</html>";
        List<ListCollection> listCollections = db.getAllListCollection(collection.getId(), -1);
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(file.getPath()))) {
            pw.println(begin);
            for (ListCollection listCollection : listCollections) {
                pw.println("<tr>");
                pw.println("<th>" + listCollection.getId() + "</th>");
                pw.println("<th>" + collection.getName() + "</th>");
                pw.println("<th>" + listCollection.getPage() + "</th>");
                pw.println("<th>" + listCollection.getMark().getId() + "</th>");
                pw.println("<th>" + listCollection.getMark().getCountry().getId() + "</th>");
                pw.println("<th>" + listCollection.getMark().getCountry().getName() + "</th>");
                pw.println("<th>" + listCollection.getMark().getSerie().getId() + "</th>");
                pw.println("<th>" + listCollection.getMark().getSerie().getName() + "</th>");
                pw.println("<th>" + listCollection.getMark().getSerie().getYear() + "</th>");

                pw.println("</tr>");
            }
            pw.println(end);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void saveTXT(File file, Collection collection) {
        List<ListCollection> listCollections = db.getAllListCollection(collection.getId(), -1);
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(file.getPath()))) {
            for (ListCollection element : listCollections) {
                pw.print(collection.getId() + " | " + collection.getName() + " | ");
                pw.println(element);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void saveXML(File file, Collection collection) {
        List<ListCollection> footballers = db.getAllListCollection(collection.getId(), -1);
        try {
            JAXBContext context = JAXBContext
                    .newInstance(ListCollectionWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Обёртываем наши данные об адресатах.
            ListCollectionWrapper wrapper = new ListCollectionWrapper();
            wrapper.setListCollection(footballers);

            // Маршаллируем и сохраняем XML в файл.
            m.marshal(wrapper, file);
        } catch (Exception e) { // catches ANY exception
            e.printStackTrace();
        }

    }

    public static void savePDF(File file, Collection collection) {
        List<ListCollection> listCollection = db.getAllListCollection(collection.getId(), -1);
        Document document = new Document(PageSize.A4, 25, 25, 25, 25);
        try {
            BaseFont times =
                    BaseFont.createFont("src/fonts/calibri.ttf", "cp1251", BaseFont.EMBEDDED);
            Font font = new Font(times, 10);
            PdfWriter.getInstance(document, new FileOutputStream(file.getPath()));
            document.open();
            float[] pointColumnWidths = {30F, 100F, 30F, 30F, 30F, 100F, 30F, 100F, 100F};
            PdfPTable table = new PdfPTable(pointColumnWidths);
            for (ListCollection el :
                    listCollection) {

                table.addCell(new Phrase(String.valueOf(collection.getId()).replaceAll(" ", ""), font));
                table.addCell(new Phrase(String.valueOf(collection.getName()).replaceAll(" ", ""), font));

                table.addCell(new Phrase(String.valueOf(el.getPage()).replaceAll(" ", ""), font));

                table.addCell(new Phrase(String.valueOf(el.getMark().getId()).replaceAll(" ", ""), font));

                table.addCell(new Phrase(String.valueOf(el.getMark().getCountry().getId()).replaceAll(" ", ""), font));
                table.addCell(new Phrase(String.valueOf(el.getMark().getCountry().getName()).replaceAll(" ", ""), font));

                table.addCell(new Phrase(String.valueOf(el.getMark().getSerie().getId()).replaceAll(" ", ""), font));
                table.addCell(new Phrase(String.valueOf(el.getMark().getSerie().getName()).replaceAll(" ", ""), font));
                table.addCell(new Phrase(String.valueOf(el.getMark().getSerie().getYear()).replaceAll(" ", ""), font));
            }
            document.add(table);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        document.close();
    }

    public static void loadPDF(File file, Collection collection){
        List<ListCollection> listCollections = new ArrayList<>();
        PdfReader reader = null;
        try {
            reader = new PdfReader(file.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // не забываем, что нумерация страниц в PDF начинается с единицы.
        for (int i = 1; i <= reader.getNumberOfPages(); ++i) {
            TextExtractionStrategy strategy = new SimpleTextExtractionStrategy();
            String text = null;
            try {
                text = PdfTextExtractor.getTextFromPage(reader, i, strategy);
                String[] words = text.split("[\n\\s]");

                for (int j = 0; j < words.length; j+=9) {
                    ListCollection listCollection = new ListCollection();

                    listCollection.setId(Integer.parseInt(words[j]));
                    listCollection.setPage(Integer.parseInt(words[j+2]));
                    listCollection.setMark(
                            new Mark(
                                    Integer.parseInt(words[j+3]),

                                    new Country(
                                            Integer.parseInt(words[j+4]),
                                            words[j+5]
                                    ),
                                    new Serie(
                                            Integer.parseInt(words[j+6]),
                                            words[j+7],
                                            Integer.parseInt(words[j+8])
                                    )
                            )
                    );

                    listCollections.add(listCollection);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!listCollections.isEmpty()) {
            db.clearCollection(collection);
            for (ListCollection lc :
                    listCollections) {
                db.addListCollection(collection, lc.getMark());
            }
        }

        // убираем за собой
        reader.close();
    }


    public static void loadHTML(File file, Collection collection) {
        String name;
        List<ListCollection> listCollections = new ArrayList<>();
        Queue<String> queue = new LinkedList<>();
        Matcher matcher;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            Pattern pattern = Pattern.compile("(?<=<th>).*(?=<\\/th>)");
            while (line != null) {
                matcher = pattern.matcher(line);
                if (matcher.find()) {
                    queue.add(matcher.group(0));
                }
                line = reader.readLine();

            }
            while (!queue.isEmpty()) {
                ListCollection lc = new ListCollection();
                lc.setId(Integer.parseInt(queue.poll()));
                name = queue.poll();//skip name
                lc.setPage(Integer.parseInt(Objects.requireNonNull(queue.poll())));
                lc.setMark(
                        new Mark(
                                Integer.parseInt(Objects.requireNonNull(queue.poll())),
                                new Country(
                                        Integer.parseInt(Objects.requireNonNull(queue.poll())),
                                        queue.poll()
                                ),
                                new Serie(
                                        Integer.parseInt(Objects.requireNonNull(queue.poll())),
                                        queue.poll(),
                                        Integer.parseInt(Objects.requireNonNull(queue.poll()))
                                )
                        )
                );
                listCollections.add(lc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!listCollections.isEmpty()) {
            db.clearCollection(collection);
            for (ListCollection lc :
                    listCollections) {
                db.addListCollection(collection, lc.getMark());
            }
        }


    }

    public static void loadTXT(File file, Collection collection) {
        List<ListCollection> listCollections = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                String[] results = line.split("\\s\\|\\s");
                for (int i = 0; i < results.length; i++) {
                    results[i] = results[i].trim();
                }
                line = reader.readLine();
                ListCollection listCollection = new ListCollection();

                listCollection.setId(Integer.parseInt(results[0]));
                listCollection.setPage(Integer.parseInt(results[2]));
                listCollection.setMark(
                        new Mark(
                                Integer.parseInt(results[3]),

                                new Country(
                                        Integer.parseInt(results[7]),
                                        results[8]
                                ),
                                new Serie(
                                        Integer.parseInt(results[4]),
                                        results[5],
                                        Integer.parseInt(results[6])
                                )
                        )
                );

                listCollections.add(listCollection);
            }
            if (!listCollections.isEmpty()) {
                db.clearCollection(collection);
                for (ListCollection lc :
                        listCollections) {
                    db.addListCollection(collection, lc.getMark());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void loadXML(File file, Collection collection) {
        List<ListCollection> listCollections = new ArrayList<>();
        try {
            JAXBContext context = JAXBContext
                    .newInstance(ListCollectionWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Чтение XML из файла и демаршализация.
            ListCollectionWrapper wrapper = (ListCollectionWrapper) um.unmarshal(file);
            listCollections.addAll(wrapper.getListCollection());
        } catch (Exception e) { // catches ANY exception
            e.printStackTrace();
        }

        if (!listCollections.isEmpty()) {
            db.clearCollection(collection);
            for (ListCollection lc :
                    listCollections) {
                db.addListCollection(collection, lc.getMark());
            }
        }
    }


}


