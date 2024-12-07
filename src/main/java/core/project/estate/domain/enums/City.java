package core.project.estate.domain.enums;

import lombok.Getter;

@Getter
public enum City {
    BAKU("Bakı"),
    SUMQAYIT("Sumqayıt"),
    GANJA("Gəncə"),
    MINGACHEVIR("Mingəçevir"),
    SHIRVAN("Şirvan"),
    NAKHCHIVAN("Naxçıvan"),
    SHAKI("Şəki"),
    LENKARAN("Lənkəran"),
    YEVLAKH("Yevlax"),
    MASALLI("Masallı"),
    SHAMAKHI("Şamaxı"),
    GABALA("Qəbələ"),
    QUBA("Quba"),
    ZAQATALA("Zaqatala"),
    IMISHLI("İmişli"),
    AGDAM("Ağdam"),
    SABIRABAD("Sabirabad"),
    SIAZAN("Siyəzən");

    private final String displayName;

    City(String displayName) {
        this.displayName = displayName;
    }

}

