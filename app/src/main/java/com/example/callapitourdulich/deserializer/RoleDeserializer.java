package com.example.callapitourdulich.deserializer;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;

public class RoleDeserializer implements JsonDeserializer<String> {
    @Override
    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        if (json.isJsonObject()) {
            JsonObject roleObj = json.getAsJsonObject();
            // Chuyển đổi object thành chuỗi, ví dụ trả về id hoặc tên
            return roleObj.get("roleName").getAsString(); // Hoặc lấy id nếu cần
        }
        return json.getAsString(); // Trả về chuỗi nguyên gốc nếu là dạng string
    }
}

