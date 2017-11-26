package map.file;

import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import djf.ui.AppMessageDialogSingleton;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import map.data.Draggable;
import static map.data.Draggable.IMAGE;
import static map.data.Draggable.LINE;
import static map.data.Draggable.TEXT;
import map.data.DraggableImage;
import map.data.DraggableLine;
import map.data.DraggableStation;
import map.data.DraggableText;
import map.data.mapData;
import map.gui.mapWorkspace;

/**
 *
 * @author Ben Michalowicz
 */
public class mapFiles implements AppFileComponent {

    //FOR JSON_LOADING
    static final String JSON_NAME = "name";
    static final String JSON_BG = "background";
    static final String JSON_RED = "red";
    static final String JSON_GREEN = "green";
    static final String JSON_BLUE = "blue";
    static final String JSON_ALPHA = "alpha";
    static final String JSON_STAT_NAMES = "station_names";
    static final String JSON_WIDTH = "width";
    static final String JSON_HEIGHT = "height";
    static final String JSON_FILL_COLOR = "fill_color";
    static final String JSON_OUTLINE_COLOR = "outline_color";
    static final String JSON_OUTLINE_THICKNESS = "outline_thickness";
    static final String JSON_IMG = "image_path";
    static final String JSON_STAT = "station";
    static final String JSON_X = "x";
    static final String JSON_Y = "y";
    static final String JSON_CIRC = "circular";
    static final String JSON_LINES = "lines";
    static final String JSON_TYPE = "type";
    static final String JSON_TEXT = "text";
    static final String JSON_FONT_SIZE = "font_size";
    static final String JSON_FONT_FAMILY = "font_family";
    static final String JSON_STYLE = "style";
    static final String JSON_COLOR = "color";
    static final String JSON_POINTS = "points";
    static final String JSON_START_X = "start_x";
    static final String JSON_START_Y = "start_y";
    static final String JSON_END_X = "end_x";
    static final String JSON_END_Y = "end_y";

    static final String DEFAULT_DOCTYPE_DECLARATION = "<!doctype html>\n";
    static final String DEFAULT_ATTRIBUTE_VALUE = "";

    mapData dataManager;

    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
        dataManager = (mapData) data;

        mapWorkspace workspace = (mapWorkspace) dataManager.getApp().getWorkspaceComponent();

        Background bg = dataManager.getB();
        //Set the background up regardless of image or color or other

        //FOR GENERAL OBJECTS
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        //FOR METRO LINES
        JsonArrayBuilder lineBuilder = Json.createArrayBuilder();
        //FOR METRO STATIONS
        JsonArrayBuilder statBuilder = Json.createArrayBuilder();
        ObservableList<Node> items = workspace.getCanvas().getChildren();

        //Let's get started creating each object
        items.forEach((node) -> {

            Node node2 = node;
            Draggable drag = (Draggable) node;

            String type = drag.getShapeType();
            double x = drag.getX();
            double y = drag.getY();
            double width = drag.getWidth();
            double height = drag.getHeight();

            if (node instanceof DraggableText) {

                DraggableText t = (DraggableText) node;

                JsonObject dText = Json.createObjectBuilder()
                        .add(JSON_TYPE, type)
                        .add(JSON_X, x)
                        .add(JSON_Y, y)
                        .add(JSON_WIDTH, width)
                        .add(JSON_HEIGHT, height)
                        .add(JSON_TEXT, t.getText())
                        .add(JSON_FONT_SIZE, t.getFont().getSize())
                        .add(JSON_FONT_FAMILY, t.getFont().getFamily())
                        .add(JSON_STYLE, t.getFont().getStyle())
                        .add(JSON_TYPE, t.getShapeType())
                        .build();
                arrayBuilder.add(dText);
            } else if (node instanceof DraggableLine) { //if not instance of Draggable Text

                DraggableLine dragged = (DraggableLine) node;
                JsonObject backgroundColor = getLineBackgroundColor(dragged.getStroke());

                JsonArrayBuilder points = Json.createArrayBuilder();

                dragged.getPoints().forEach((point) -> {
                    points.add(point);
                });

                //TODO: Implement isCircular
                JsonArray listPoints = points.build(); //Add all the coordinate points to the line

                JsonArrayBuilder stationNames = Json.createArrayBuilder();

                dragged.getStations().forEach((stat) -> {
                    stationNames.add(stat);
                });

                JsonArray statNames = stationNames.build(); //Add all the station names

                JsonObject jsonLine = Json.createObjectBuilder()
                        .add(JSON_NAME, dragged.getName())
                        .add(JSON_COLOR, backgroundColor)
                        .add(JSON_CIRC, false) //TODO: Implement Circular Lines
                        .add(JSON_STAT_NAMES, statNames)
                        .add(JSON_POINTS, listPoints)
                        .add(JSON_START_X, dragged.getStartName().getX() + 25)
                        .add(JSON_START_Y, dragged.getStartName().getY())
                        .add(JSON_END_X, dragged.getEndName().getX() - 5)
                        .add(JSON_END_Y, dragged.getEndName().getY())
                        .add(JSON_TYPE, dragged.getShapeType())
                        .build();
                lineBuilder.add(jsonLine);

            } else if (node instanceof DraggableStation) {
                DraggableStation statMan = (DraggableStation) node;

                JsonObject color = getLineBackgroundColor(statMan.getFill());

                JsonObject jsonStat = Json.createObjectBuilder()
                        .add(JSON_NAME, statMan.getName())
                        .add(JSON_X, statMan.getCenterX())
                        .add(JSON_Y, statMan.getCenterY())
                        .add(JSON_TYPE, statMan.getShapeType())
                        .add(JSON_COLOR, color)
                        .add("JSON_RADIUS_X", statMan.getRadiusX())
                        .add("JSON_RADIUS_Y", statMan.getRadiusY())
                        .build();
                statBuilder.add(jsonStat);

            } else if (node instanceof DraggableImage) {
                DraggableImage image = (DraggableImage) node;
                JsonObject jsonImage = Json.createObjectBuilder()
                        .add(JSON_X, image.getX())
                        .add(JSON_Y, image.getY())
                        .add(JSON_WIDTH, image.getWidth())
                        .add(JSON_HEIGHT, image.getHeight())
                        .add(JSON_IMG, image.getFilePath()) //TODO: USE PATHFINDER THING IN HW 6
                        .add(JSON_TYPE, image.getShapeType())
                        .build();
                arrayBuilder.add(jsonImage);
            }
        });

        JsonArray genItems = arrayBuilder.build();
        JsonArray metroLines = lineBuilder.build();
        JsonArray metroStat = statBuilder.build();

        JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_NAME, dataManager.getLineName())
                .add(JSON_LINES, metroLines)
                .add(JSON_STAT, metroStat)
                .add("everything_else", genItems)
                .build();

        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
        Map<String, Object> properties = new HashMap<>(1);
        properties.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
        StringWriter sw = new StringWriter();
        try (JsonWriter jsonWriter = writerFactory.createWriter(sw)) {
            jsonWriter.writeObject(dataManagerJSO);
        }

        // INIT THE WRITER
        OutputStream os = new FileOutputStream(filePath);
        JsonWriter jsonFileWriter = Json.createWriter(os);
        jsonFileWriter.writeObject(dataManagerJSO);
        String prettyPrinted = sw.toString();
        try (PrintWriter pw = new PrintWriter(filePath)) {
            pw.write(prettyPrinted);
        }
    }

    @Override

    public void loadData(AppDataComponent data, String filePath) throws IOException {
        dataManager = (mapData) data;
        dataManager.resetData();

        JsonObject json = loadJSONFile(filePath); //TODO: Implement

        dataManager.getApp().setLineName(json.getString(JSON_NAME));

        //dataManager.setBackgroundColor(Color.LIGHTGREY);
        JsonArray jsonItemsArray = json.getJsonArray("everything_else");
        JsonArray jsonLinesArray = json.getJsonArray(JSON_LINES);
        JsonArray jsonStatArray = json.getJsonArray(JSON_STAT);
        
        
          for (int i = 0; i < jsonLinesArray.size(); i++) {
            JsonObject jsonItem = jsonLinesArray.getJsonObject(i);
            Node node = loadNode(jsonItem);
            
                dataManager.addShape(node);
            
            
        }
        
        for (int i = 0; i < jsonStatArray.size(); i++) {
            JsonObject jsonItem = jsonStatArray.getJsonObject(i);

            DraggableStation node = new DraggableStation(dataManager.getApp(), "");
            node.setName(jsonItem.getString(JSON_NAME));
            node.setCenterX(jsonItem.getInt(JSON_X));
            node.setCenterY(jsonItem.getInt(JSON_Y));
            node.setFill(loadColor(jsonItem, JSON_COLOR));
            node.setRadiusX(jsonItem.getInt("JSON_RADIUS_X"));
            node.setRadiusY(jsonItem.getInt("JSON_RADIUS_Y"));

            dataManager.addShape(node);

            ((mapWorkspace) dataManager.getApp().getWorkspaceComponent()).getStations().getItems().add(node.getName());
            ((mapWorkspace) dataManager.getApp().getWorkspaceComponent()).getFromStat().getItems().add(node.getName());
            ((mapWorkspace) dataManager.getApp().getWorkspaceComponent()).getToStat().getItems().add(node.getName());
        }

        for (int i = 0; i < jsonItemsArray.size(); i++) {
            JsonObject jsonItem = jsonItemsArray.getJsonObject(i);
            Node node = loadNode(jsonItem);
            
            
            if(!dataManager.getShapeList().contains(node)){
                dataManager.addShape(node);
            }
        }

      

        

    }

    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
        JsonObject json;
        try (InputStream is = new FileInputStream(jsonFilePath);
                JsonReader jsonReader = Json.createReader(is)) {
            json = jsonReader.readObject();
        }
        return json;
    }

    private Color loadColor(JsonObject json, String colorToGet) {
        JsonObject jsonColor = json.getJsonObject(colorToGet);
        double red = getDataAsDouble(jsonColor, JSON_RED);
        double green = getDataAsDouble(jsonColor, JSON_GREEN);
        double blue = getDataAsDouble(jsonColor, JSON_BLUE);
        double alpha = getDataAsDouble(jsonColor, JSON_ALPHA);
        Color loadedColor = new Color(red, green, blue, alpha);

        return loadedColor;

    }

    private double getDataAsDouble(JsonObject json, String dataName) {
        JsonValue value = json.get(dataName);
        JsonNumber number = (JsonNumber) value;
        return number.bigDecimalValue().doubleValue();
    }

    private Node loadNode(JsonObject jsonObject) {
        String type = jsonObject.getString(JSON_TYPE);

        Node retVal = null;

        switch (type) {
            case IMAGE:
                retVal = new DraggableImage(dataManager.getApp());
                break;
            case TEXT:
                retVal = new DraggableText(dataManager.getApp());
                break;
            case LINE:
                retVal = new DraggableLine(dataManager.getApp(), "");
                break;

        }

        if (retVal instanceof DraggableImage) {

            String filePath = "file:///" + jsonObject.getString(JSON_IMG);

            try {
                URL url = new URL(filePath);
                Image img = new Image(url.toExternalForm());
                ((DraggableImage) retVal).setFill(new ImagePattern(img));
                ((DraggableImage) retVal).setWidth(img.getWidth());
                ((DraggableImage) retVal).setHeight(img.getHeight());
                ((DraggableImage) retVal).setX(jsonObject.getInt(JSON_X));
                ((DraggableImage) retVal).setY(jsonObject.getInt(JSON_Y));

            } catch (MalformedURLException mue) {
                AppMessageDialogSingleton.getSingleton().show("Image Loading Error", "There was an error loading an image");
            }

        } else if (retVal instanceof DraggableText) {
            ((DraggableText) retVal).setText(jsonObject.getString(JSON_TEXT));
            ((DraggableText) retVal).setFont(Font.font(jsonObject.getString(JSON_FONT_FAMILY), FontWeight.NORMAL, FontPosture.REGULAR, jsonObject.getInt(JSON_FONT_SIZE)));
            ((DraggableText) retVal).setX(jsonObject.getInt(JSON_X));
            ((DraggableText) retVal).setY(jsonObject.getInt(JSON_Y));

        } else if (retVal instanceof DraggableLine) {
            ((DraggableLine) retVal).setName(jsonObject.getString(JSON_NAME));

            JsonArray statNames = jsonObject.getJsonArray(JSON_STAT_NAMES);

            for (int i = 0; i < statNames.size(); i++) {
                ((DraggableLine) retVal).getStations().add(statNames.getString(i));
            }
            ((DraggableLine) retVal).setStroke(loadColor(jsonObject, JSON_COLOR));

            JsonArray linePoints = jsonObject.getJsonArray(JSON_POINTS);

            for (int i = 4; i < linePoints.size(); i++) {
                ((DraggableLine) retVal).getPoints().add((double) linePoints.getInt(i));

            }

            int x = linePoints.getInt(0);
            int y = linePoints.getInt(1);

            ((DraggableLine) retVal).start(x, y);

            ((mapWorkspace) dataManager.getApp().getWorkspaceComponent()).getLines().getItems().add(((DraggableLine) retVal).getName());

            ((DraggableLine) retVal).setStrokeWidth(5);

        } //DraggableStation has its own thing

        return retVal;

    }

    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
        dataManager = (mapData) data;

        mapWorkspace workspace = (mapWorkspace) dataManager.getApp().getWorkspaceComponent();

        Background bg = dataManager.getB();
        //Set the background up regardless of image or color or other

        //We only care about metroLines and Stations in the file exporting
        //FOR METRO LINES
        JsonArrayBuilder lineBuilder = Json.createArrayBuilder();
        //FOR METRO STATIONS
        JsonArrayBuilder statBuilder = Json.createArrayBuilder();
        ObservableList<Node> items = workspace.getCanvas().getChildren();

        //Let's get started creating each object
        items.forEach((node) -> {

            Node node2 = node;
            Draggable drag = (Draggable) node;

            String type = drag.getShapeType();
            double x = drag.getX();
            double y = drag.getY();
            double width = drag.getWidth();
            double height = drag.getHeight();

            if (node instanceof DraggableLine) { //if not instance of Draggable Text

                DraggableLine dragged = (DraggableLine) node;
                JsonObject backgroundColor = getLineBackgroundColor(dragged.getStroke());

                //TODO: Implement isCircular
                JsonArrayBuilder stationNames = Json.createArrayBuilder();

                dragged.getStations().forEach((stat) -> {
                    stationNames.add(stat);
                });

                JsonArray statNames = stationNames.build(); //Add all the station names

                JsonObject jsonLine = Json.createObjectBuilder()
                        .add(JSON_NAME, dragged.getName())
                        .add(JSON_CIRC, false) //TODO: Implement Circular Lines
                        .add(JSON_COLOR, backgroundColor)
                        .add(JSON_STAT_NAMES, statNames)
                        .build();
                lineBuilder.add(jsonLine);

            } else if (node instanceof DraggableStation) {
                DraggableStation statMan = (DraggableStation) node;

                JsonObject color = getLineBackgroundColor(statMan.getFill());

                JsonObject jsonStat = Json.createObjectBuilder()
                        .add(JSON_NAME, statMan.getName())
                        .add(JSON_X, statMan.getCenterX())
                        .add(JSON_Y, statMan.getCenterY())
                        .build();
                statBuilder.add(jsonStat);

            }
        });

        JsonArray metroLines = lineBuilder.build();
        JsonArray metroStat = statBuilder.build();

        JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_NAME, dataManager.getLineName())
                .add(JSON_LINES, metroLines)
                .add(JSON_STAT, metroStat)
                .build();

        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
        Map<String, Object> properties = new HashMap<>(1);
        properties.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
        StringWriter sw = new StringWriter();
        try (JsonWriter jsonWriter = writerFactory.createWriter(sw)) {
            jsonWriter.writeObject(dataManagerJSO);
        }

        // INIT THE WRITER
        OutputStream os = new FileOutputStream(filePath);
        JsonWriter jsonFileWriter = Json.createWriter(os);
        jsonFileWriter.writeObject(dataManagerJSO);
        String prettyPrinted = sw.toString();
        try (PrintWriter pw = new PrintWriter(filePath)) {
            pw.write(prettyPrinted);
        }
    }

    // THIS METHOD WILL NOT BE USED FOR THE DURATION OF THIS PROJECT
    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private JsonObject getLineBackgroundColor(Paint stroke) {
        Color color = (Color) stroke;
        JsonObject colorJson = Json.createObjectBuilder()
                .add(JSON_RED, color.getRed())
                .add(JSON_GREEN, color.getGreen())
                .add(JSON_BLUE, color.getBlue())
                .add(JSON_ALPHA, color.getOpacity()).build();
        return colorJson;
    }

}
