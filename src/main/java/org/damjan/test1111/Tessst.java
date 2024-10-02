package org.damjan.test1111;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tessst {
    public static void main(String[] args) {
        List<Map<String, Object>> table = new ArrayList<>();

        Map<String, Object> row1 = new HashMap<>();
        row1.put("Header parameter", "Content-Type");

        Map<String, Object> row2 = new HashMap<>();
        row2.put("Header parameter value", "application/test");

        Map<String, Object> row3 = new HashMap<>();
        row3.put("Header parameter", "Accept");

        Map<String, Object> row4 = new HashMap<>();
        row4.put("Header parameter value", "xml/test");

        Map<String, Object> row5 = new HashMap<>();
        row5.put("Header parameter value", "Petar");

        Map<String, Object> row6 = new HashMap<>();
        row6.put("Header parameter value", "Manchevski");


        table.add(row1);
        table.add(row2);
        table.add(row3);
        table.add(row4);
        table.add(row5);
        table.add(row6);

        // Iterira vo listata od mapi i kombinira par so red
        for (int i = 0; i < table.size(); i += 2) {
            Map<String, Object> keyMap = table.get(i);
            Map<String, Object> valueMap = table.get(i + 1);

            String key = (String) keyMap.values().iterator().next();
            String value = (String) valueMap.values().iterator().next();

            // Print the combined key-value pair
            System.out.println("\"" + key + "\": \"" + value + "\"");
        }
    }
}
