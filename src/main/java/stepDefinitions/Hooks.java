package stepDefinitions;

import cucumber.api.Scenario;
import org.apache.log4j.Logger;
import org.junit.Before;

import java.util.Collection;


public class Hooks {
    private static Collection<String> taggs;
    public static Scenario scenario;
    private static String hostname;
    private static String responseJson;
    private static Logger log;



    @Before
    public void runBeforeWithOrder(Scenario scenario) throws Throwable{
        Hooks.setScenario(scenario);
        KeepSnarion(scenario);
    }

    public static void KeepSnarion(Scenario scenario){
        setTaggs(scenario.getSourceTagNames());
    }

    public static Collection<String> getTaggs(){
        return taggs;
    }
    public static void setTaggs(Collection<String> taggs){
        Hooks.taggs = taggs;
    }

    public static Scenario getScenario(){
        return scenario;
    }
    public static void setScenario(Scenario scenario){
        Hooks.scenario = scenario;
    }

    public static String getHostname(){
        return  hostname;
    }

    public static void setHostname(String hostname){
        Hooks.hostname = hostname;
    }

    public static String getResponseJson(){
        return  responseJson;
    }

    public static void setResponseJson(String responseJson){
        Hooks.responseJson = responseJson;
    }

    public static  Logger getLog(){
        return getLog();
    }

    public static void setLog(Logger log){
        Hooks.log = log;
    }

}
