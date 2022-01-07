package sample.Utils;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import javafx.scene.control.ContextMenu;
import javafx.stage.FileChooser;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import sample.DB.DbHandler;
import sample.Model.Command;
import sample.Model.Footballer;
import sample.Model.FootballerListWrapper;
import sample.Model.Specialization;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class FileManager {

    private static final DbHandler db = DbHandler.getInstance();

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

    public static void saveHTML(File file) {
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
        java.util.List<Footballer> footballers = db.getAllFootballers();
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(file.getPath()))) {
            pw.println(begin);
            for (Footballer footballer : footballers) {
                pw.println("<tr>");
                pw.println("<th>" + footballer.getId() + "</th>");
                pw.println("<th>" + footballer.getName() + "</th>");
                pw.println("<th>" + footballer.getSpecialization().getId() + "</th>");
                pw.println("<th>" + footballer.getSpecialization().getRole() + "</th>");
                pw.println("<th>" + footballer.getAllGames() + "</th>");
                pw.println("<th>" + footballer.getSuccessGames() + "</th>");
                pw.println("<th>" + footballer.getCommand().getId() + "</th>");
                pw.println("<th>" + footballer.getCommand().getName() + "</th>");
                pw.println("</tr>");
            }
            pw.println(end);
        } catch (FileNotFoundException e) {
            Alerts.alertError("Ошибка", "Ошибка сохранения");
            e.printStackTrace();
        }

    }

    public static void saveTXT(File file) {
        java.util.List<Footballer> footballers = db.getAllFootballers();
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(file.getPath()))) {
            for (Footballer footballer : footballers)
                pw.println(footballer);
        } catch (FileNotFoundException e) {
            Alerts.alertError("Ошибка", "Ошибка сохранения");
            e.printStackTrace();
        }

    }

    public static void saveXML(File file) {
        java.util.List<Footballer> footballers = db.getAllFootballers();
        try {
            JAXBContext context = JAXBContext
                    .newInstance(FootballerListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Обёртываем наши данные об адресатах.
            FootballerListWrapper wrapper = new FootballerListWrapper();
            wrapper.setFootballers(footballers);

            // Маршаллируем и сохраняем XML в файл.
            m.marshal(wrapper, file);
        } catch (Exception e) { // catches ANY exception
            Alerts.alertError("Ошибка", "Ошибка сохранения");
            e.printStackTrace();
        }

    }

    public static void savePDF(File file) {
        java.util.List<Footballer> footballers = db.getAllFootballers();
        Document document = new Document(PageSize.A4, 25, 25, 25, 25);
        try {
            BaseFont times =
                    BaseFont.createFont("src/fonts/calibri.ttf","cp1251",BaseFont.EMBEDDED);
            Font font = new Font(times, 10);
            PdfWriter.getInstance(document, new FileOutputStream(file.getPath()));
            document.open();
            float [] pointColumnWidths = {20F, 100F, 20F, 100F, 35F, 35F, 20F, 100F};
            PdfPTable table = new PdfPTable(pointColumnWidths);
            for (Footballer f :
                    footballers) {
                table.addCell(new Phrase(String.valueOf(f.getId()).replaceAll(" ", ""), font));
                table.addCell(new Phrase(String.valueOf(f.getName()).replaceAll(" ", ""), font));

                table.addCell(new Phrase(String.valueOf(f.getSpecialization().getId()).replaceAll(" ", ""), font));
                table.addCell(new Phrase((f.getSpecialization().getRole()).replaceAll(" ", ""), font));

                table.addCell(new Phrase(String.valueOf(f.getAllGames()).replaceAll(" ", ""), font));
                table.addCell(new Phrase(String.valueOf(f.getSuccessGames()).replaceAll(" ", ""), font));

                table.addCell(new Phrase(String.valueOf(f.getCommand().getId()).replaceAll(" ", ""), font));
                table.addCell(new Phrase(String.valueOf(f.getCommand().getName()).replaceAll(" ", ""), font));
            }
            document.add(table);
        } catch (DocumentException | IOException e) {
            Alerts.alertError("Ошибка", "Ошибка сохранения");
            e.printStackTrace();
        }
        document.close();
    }

    public static void loadPDF(File file){
        java.util.List<Footballer> footballers = new ArrayList<>();
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

                for (int j = 0; j < words.length; j+=8) {
                    Footballer footballer = new Footballer();
                    footballer.setId(Integer.parseInt(words[j]));
                    footballer.setName(words[j+1]);

                    footballer.setSpecialization(new Specialization(
                            Integer.parseInt(words[j+2]),
                            words[j+3]
                    ));
                    footballer.setSuccessGames(Integer.parseInt(words[j+4]));
                    footballer.setAllGames(Integer.parseInt(words[j+5]));
                    footballer.setCommand(new Command(
                            Integer.parseInt(words[j+6]),
                            words[j+7]
                    ));

                    footballers.add(footballer);

                }
            } catch (IOException e) {
                e.printStackTrace();
                Alerts.alertError("Ошибка", "Ошибка загрузки!");
            }
        }
        if (footballers.size() != 0){
            db.clearFootballers();
            for (Footballer f :
                    footballers) {
                db.addFootballer(f);
            }
            Alerts.alertInfo("Успех", "Файл загружен");
        } else {
            Alerts.alertError("Ошибка", "Файл пуст!");
        }

        // убираем за собой
        reader.close();
    }

    public static void loadHTML(File file) {
        java.util.List<Footballer> footballers = new ArrayList<>();
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
                Footballer footballer = new Footballer();
                footballer.setId(Integer.parseInt(Objects.requireNonNull(queue.poll())));
                footballer.setName(queue.poll());
                footballer.setSpecialization(new Specialization(
                        Integer.parseInt(Objects.requireNonNull(queue.poll())),
                        queue.poll()
                ));
                footballer.setSuccessGames(Integer.parseInt(Objects.requireNonNull(queue.poll())));
                footballer.setAllGames(Integer.parseInt(Objects.requireNonNull(queue.poll())));
                footballer.setCommand(new Command(
                        Integer.parseInt(Objects.requireNonNull(queue.poll())),
                        queue.poll()
                ));
                footballers.add(footballer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.alertError("Ошибка", "Ошибка загрузки!");
        }

        if (footballers.size() != 0){
            db.clearFootballers();
            for (Footballer f :
                    footballers) {
                db.addFootballer(f);
            }
            Alerts.alertInfo("Успех", "Файл загружен");
        } else {
            Alerts.alertError("Ошибка", "Файл пуст!");
        }
    }

    public static void loadTXT(File file) {
        java.util.List<Footballer> footballers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                String[] results = line.split("\\|");
                for (int i = 0; i < results.length; i++) {
                    results[i] = results[i].trim();
                }
                line = reader.readLine();
                Footballer footballer = new Footballer();

                footballer.setName(results[0]);
                footballer.setSpecialization(new Specialization(
                        Integer.parseInt(results[1]),
                        results[2]
                ));
                footballer.setCommand(new Command(
                        Integer.parseInt(results[3]),
                        results[4]
                ));
                footballer.setSuccessGames(Integer.parseInt(results[5]));
                footballer.setAllGames(Integer.parseInt(results[6]));

                footballers.add(footballer);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Alerts.alertError("Ошибка", "Ошибка загрузки!");
        }

        if (footballers.size() != 0){
            db.clearFootballers();
            for (Footballer f :
                    footballers) {
                db.addFootballer(f);
            }
            Alerts.alertInfo("Успех", "Файл загружен");
        } else {
            Alerts.alertError("Ошибка", "Файл пуст!");
        }
    }

    public static void loadXML(File file) {
        List<Footballer> footballers = new ArrayList<>();
        try {
            JAXBContext context = JAXBContext
                    .newInstance(FootballerListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Чтение XML из файла и демаршализация.
            FootballerListWrapper wrapper = (FootballerListWrapper) um.unmarshal(file);
            footballers.addAll(wrapper.getFootballers());
        } catch (Exception e) { // catches ANY exception
            Alerts.alertError("Could not load data", "Could not load data from file" + file.getPath());
        }

        if (footballers.size() != 0){
            db.clearFootballers();
            for (Footballer f :
                    footballers) {
                db.addFootballer(f);
            }
            Alerts.alertInfo("Успех", "Файл загружен");
        } else {
            Alerts.alertError("Ошибка", "Файл пуст!");
        }
    }


}