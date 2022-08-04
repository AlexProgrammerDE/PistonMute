package net.pistonmaster.pistonmute.utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

@SuppressWarnings("deprecation")
public class MojangUtil {

    private static final String UUID_URL = "https://api.mojang.com/users/profiles/minecraft/%s";
    private static final String NAME_URL = "https://api.mojang.com/user/profiles/%s/names";

    @SneakyThrows
    public static UUID getUUID(String name) {
        String UUIDJson = IOUtils.toString(new URL(String.format(UUID_URL, name)));

        if(UUIDJson.isEmpty()) return null;

        JSONObject UUIDObject = (JSONObject) JSONValue.parseWithException(UUIDJson);

        return UUIDTypeAdapter.fromString(UUIDObject.get("id").toString());
    }

    @SneakyThrows
    public static String getName(UUID uuid) {
        String nameJson = IOUtils.toString(new URL(String.format(NAME_URL, UUIDTypeAdapter.fromUUID(uuid))));

        JSONArray nameValue = (JSONArray) JSONValue.parseWithException(nameJson);

        String playerSlot = nameValue.get(nameValue.size()-1).toString();

        JSONObject nameObject = (JSONObject) JSONValue.parseWithException(playerSlot);

        return nameObject.get("name").toString();
    }

    private static class UUIDTypeAdapter extends TypeAdapter<UUID> {
        public void write(final JsonWriter out, final UUID value) throws IOException {
            out.value(fromUUID(value));
        }

        public UUID read(final JsonReader in) throws IOException {
            return fromString(in.nextString());
        }

        public static String fromUUID(final UUID value) {
            return value.toString().replace("-", "");
        }

        public static UUID fromString(final String input) {
            return UUID.fromString(input.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
        }
    }
}
