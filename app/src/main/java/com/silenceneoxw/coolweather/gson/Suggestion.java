package com.silenceneoxw.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * @Name : Suggestion
 * @Date : 2017/10/24 13:57
 * @Author : Silenceneo
 * @E-mail : silenceneoxw@gmail.com
 * @Link : https://github.com/silenceneo-xw
 */

public class Suggestion {

    @SerializedName("air")
    public Air air;

    @SerializedName("comf")
    public Comfort comfort;

    @SerializedName("cw")
    public CarWash carWash;

    @SerializedName("drsg")
    public Dress dress;

    @SerializedName("flu")
    public Flu flu;

    @SerializedName("sport")
    public Sport sport;

    @SerializedName("trav")
    public Travel travel;

    @SerializedName("uv")
    public Ultraviolet ultraviolet;

    public class Air {

        @SerializedName("txt")
        public String info;
    }

    public class Comfort {

        @SerializedName("txt")
        public String info;
    }

    public class CarWash {

        @SerializedName("txt")
        public String info;
    }

    public class Dress {

        @SerializedName("txt")
        public String info;
    }

    public class Flu {

        @SerializedName("txt")
        public String info;
    }

    public class Sport {

        @SerializedName("txt")
        public String info;
    }

    public class Travel {

        @SerializedName("txt")
        public String info;
    }

    public class Ultraviolet {

        @SerializedName("txt")
        public String info;
    }
}
