package com.frejo.sfuser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class User {
    @JsonProperty(value="Id")           public String id;
    @JsonProperty(value="Username")     public String username;
    @JsonProperty(value="Name")         public String name;
    @JsonProperty(value="Title")        public String title;
}
