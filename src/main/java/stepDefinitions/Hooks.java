package stepDefinitions;

import cucumber.api.Scenario;
import cucumber.api.java.Before;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Hooks {

    private static Collection<String> tags;
    static Scenario scenario;

    private static boolean exceptException;


    private static List<Exception> exceptions = new ArrayList<>();

    @Before
    public void renBeforeWithOrder(Scenario scenario) throws Throwable{
        Hooks.scenario = scenario;
        KeepScenarion(scenario);
    }

    public void KeepScenarion(Scenario scenario){
        Hooks.tags = scenario.getSourceTagNames();
    }

    public void expectException() {
        exceptException = true;
    }

    public static void add(Exception e) throws Exception{
        if (!exceptException){
            throw  e;
        }
        exceptions.add(e);
    }

    public static List<Exception> getExceptions(){
        return  exceptions;
    }

    public static void screenShotAlert() throws Throwable{
        Rectangle screenRect = new Rectangle(0, 0, 0, 0);
        for (GraphicsDevice gd : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()){
            screenRect = screenRect.union(gd.getDefaultConfiguration().getBounds());
        }
        BufferedImage image = new Robot().createScreenCapture(screenRect);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos
                .close();

        scenario.embed(imageInByte, "image/png");
    }

}
