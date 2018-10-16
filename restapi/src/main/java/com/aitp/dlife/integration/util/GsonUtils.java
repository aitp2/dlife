package com.aitp.dlife.integration.util;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class GsonUtils {
	public static final Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	public static final Gson longDateGson = (new GsonBuilder()).registerTypeAdapter(Date.class, new JsonDeserializer() {
		public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			return new Date(json.getAsJsonPrimitive().getAsLong());
		}
	}).setDateFormat(1).create();
}