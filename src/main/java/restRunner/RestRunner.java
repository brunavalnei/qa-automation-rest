package restRunner;


import common.utils.FileManagerUtils;
import common.utils.PropertiesUtil;
import cucumber.api.cli.Main;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import reports.ReportJson;
import stepDefinitions.Hooks;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.util.Objects.nonNull;


public class RestRunner {

    public static void main(String[] args) throws IOException {

        Hooks.setLog(Logger.getLogger(RestRunner.class));


        String TEST_FOLDER = PropertiesUtil.getProperty("features.folder");
        String TEMP_FOLDER = "features";
        String featuresNames = PropertiesUtil.getProperty("features.names");
//        Hooks.getLog().setLevel(Level.ERROR);

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dD HH-mm-ss");
        Date date = new Date();
        String reportDirName = "Run";
        String reportRoot = "cucumber-html-reports/";
        String reportDir = reportRoot + reportDirName + "/";
        reportDirName = reportDirName.replace("", "");
        reportDir = reportDir.replace("", "");

        ReportJson.clearReportDir(reportRoot);

        FileManagerUtils fUtils = new FileManagerUtils();

        BasicConfigurator.configure();

        String execFeaturesFolder = null;
        if (nonNull(featuresNames)) {
            String[] FEATURES = featuresNames.split(",");
            fUtils.tempFeatureCreate(TEMP_FOLDER, TEST_FOLDER, FEATURES);
            execFeaturesFolder = TEMP_FOLDER;
        } else {
            execFeaturesFolder = fUtils.execFeaturesFolder(TEMP_FOLDER, TEST_FOLDER);
        }

//        configuração do caminho para stepDefinitions
        String glue = "scr/main/java/stepDefinitions/restApi";
        System.out.println(glue);

        String[] plugins = {"pretty", "json:" + reportDir + reportDirName + ".json"};
        String[] arguments = {"-m", "-p", plugins[0], "-p", plugins[1], "-g", glue, execFeaturesFolder};

        if (args.length != 0) {
            String tags = "";
            for (String arg : args) {
                tags = tags + arg + ",";
            }

            tags = tags.substring(0, tags.length() - 1);

            arguments = ArrayUtils.add(arguments, "-t");
            arguments = ArrayUtils.add(arguments, tags);
        }

        try {
            Main.run(arguments, Thread.currentThread().getContextClassLoader());
        } catch (Throwable e) {
            e.printStackTrace();
        }

        ReportJson.generateFinalReport(reportDir, reportDir, "Report " + reportDirName);


//        relatório
        String reportAbsPath = new File(reportDir).getAbsolutePath();
        String report = reportAbsPath + "cucumber-html-reports/overview-features.html";

        System.out.println("REPORT GENERATED: ");
        System.out.println(report);

//        if (nonNull(featuresNames))
//            fUtils.tempDirectoryDelete(TEMP_FOLDER);
    }
}
