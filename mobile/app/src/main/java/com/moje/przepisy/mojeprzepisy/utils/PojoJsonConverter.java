package com.moje.przepisy.mojeprzepisy.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moje.przepisy.mojeprzepisy.data.model.Ingredient;
import com.moje.przepisy.mojeprzepisy.data.model.Step;
import java.lang.reflect.Type;
import java.util.List;

public class PojoJsonConverter {
    private Gson gson = new Gson();

    public <T> String convertPojoToJson(List<T> list) {
        Type type = new TypeToken<List<T>>(){}.getType();
        return gson.toJson(list, type);
    }

    public <T> List<T> convertJsonToPojo(String jsonList, String classType) {
        Type type;
        if(classType.equals(Constant.INGREDIENTS_FILE_NAME)){
            type = new TypeToken<List<Ingredient>>() {}.getType();
        }else {
            type = new TypeToken<List<Step>>() {}.getType();
        }

        return gson.fromJson(jsonList, type);
    }
}
