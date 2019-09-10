package test.source;

import data.messages.InputData;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataSource implements SourceFunction<InputData> {
    @Override
    public void run(SourceContext<InputData> sourceContext) throws Exception {
        File file = new File("/home/stefan5andonov/work/realtimestatsanalysis/src/main/java/input_data2");

        RandomAccessFile raf = new RandomAccessFile(file, "r");

        long filePointer = 0;
        long fileEnd = raf.length();
        raf.seek(filePointer);

        StringBuilder sb = new StringBuilder();
        while (filePointer<fileEnd) {
            byte b;

            b = raf.readByte();

            if (b=='{') {
                sb = new StringBuilder();
                sb.append('{');
            }
            else if (b=='}') {
                sb.append('}');
                sourceContext.collect(new InputData(sb.toString()));
            }
            else {
                sb.append((char) b);
            }
            ++filePointer;
        }


//        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
//        List<String> arrayList = bufferedReader.lines().collect(Collectors.toList());
//        List<InputData> protocolMessages = arrayList.stream()
//                .map(s -> new InputData(s))
//                .collect(Collectors.toList());
//
//        for (int i = 0; i < 1 ; ++i) {
//
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            protocolMessages.stream()
//                    .forEach(protocolMessage ->
//                    {
//                        try {
//                            Thread.sleep(100);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//
//                        sourceContext.collect(protocolMessage);
////                        pr.println(protocolMessage.getMessage());
//
////                        out.println(protocolMessage.getMessage());
//                    });
//
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public void cancel() {

    }
}
