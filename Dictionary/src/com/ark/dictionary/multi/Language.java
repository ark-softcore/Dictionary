package com.ark.dictionary.multi;

public enum Language {
	UNDEFINED("Salect Language", "undef"),

	AFRIKAANS("Afrikaans", "af"),

	ALBANIAN("Albanian", "sq"),

	ARABIC("Arabic", "ar"),

	ARMENIAN("Armenian", "hy"),

	AZERBAIJANI("Azerbaijani", "az"),

	BASQUE("Basque", "eu"),

	BELARUSIAN("Belarusian", "be"),

	BENGALI("Bengali", "bn"),

	BOSNIAN("Bosnian", "bs"),

	BULGARIAN("Bulgarian", "bg"),

	CATALAN("Catalan", "ca"),

	CEBUANO("Cebuano", "ceb"),

	CHINESE("Chinese", "zh-CN"),

	CROATIAN("Croatian", "hr"),

	CZECH("Czech", "cs"),

	DANISH("Danish", "da"),

	DUTCH("Dutch", "nl"),

	ENGLISH("English", "en"),

	ESPERANTO("Esperanto", "eo"),

	ESTONIAN("Estonian", "et"),

	FILIPINO("Filipino", "tl"),

	FINNISH("Finnish", "fi"),

	FRENCH("French", "fr"),

	GALICIAN("Galician", "gl"),

	GEORGIAN("Georgian", "ka"),

	GERMAN("German", "de"),

	GREEK("Greek", "el"),

	GUJARATI("Gujarati", "gu"),

	HAITIAN_CREOLE("Haitian Creole", "ht"),

	HAUSA("Hausa", "ha"),

	HEBREW("Hebrew", "iw"),

	HINDI("Hindi", "hi"),

	HMONG("Hmong", "hmn"),

	HUNGARIAN("Hungarian", "hu"),

	ICELANDIC("Icelandic", "is"),

	IGBO("Igbo", "ig"),

	INDONESIAN("Indonesian", "id"),

	IRISH("Irish", "ga"),

	ITALIAN("Italian", "it"),

	JAPANESE("Japanese", "ja"),

	JAVANESE("Javanese", "jw"),

	KANNADA("Kannada", "kn"),

	KHMER("Khmer", "km"),

	KOREAN("Korean", "ko"),

	LAO("Lao", "lo"),

	LATIN("Latin", "la"),

	LATVIAN("Latvian", "lv"),

	LITHUANIAN("Lithuanian", "lt"),

	MACEDONIAN("Macedonian", "mk"),

	MALAY("Malay", "ms"),

	MALTESE("Maltese", "mt"),

	MAORI("Maori", "mi"),

	MARATHI("Marathi", "mr"),

	MONGOLIAN("Mongolian", "mn"),

	NEPALI("Nepali", "ne"),

	NORWEGIAN("Norwegian", "no"),

	PERSIAN("Persian", "fa"),

	POLISH("Polish", "pl"),

	PORTUGUESE("Portuguese", "pt"),

	PUNJABI("Punjabi", "pa"),

	ROMANIAN("Romanian", "ro"),

	RUSSIAN("Russian", "ru"),

	SERBIAN("Serbian", "sr"),

	SLOVAK("Slovak", "sk"),

	SLOVENIAN("Slovenian", "sl"),

	SOMALI("Somali", "so"),

	SPANISH("Spanish", "es"),

	SWAHILI("Swahili", "sw"),

	SWEDISH("Swedish", "sv"),

	TAMIL("Tamil", "ta"),

	TELUGU("Telugu", "te"),

	THAI("Thai", "th"),

	TURKISH("Turkish", "tr"),

	UKRAINIAN("Ukrainian", "uk"),

	URDU("Urdu", "ur"),

	VIETNAMESE("Vietnamese", "vi"),

	WELSH("Welsh", "cy"),

	YIDDISH("Yiddish", "yi"),

	YORUBA("Yoruba", "yo"),

	ZULU("Zulu", "zu");

	public String code;
	public String name;

	private Language(String name, String code) {
		this.name = name;
		this.code = code;
	}

	public static Language getLanguageByCode(String code) {
		Language[] values = Language.values();
		for (int i = 0; i < values.length; i++) {
			if (values[i].code.equals(code)) {
				return values[i];
			}
		}

		return null;
	}
}
