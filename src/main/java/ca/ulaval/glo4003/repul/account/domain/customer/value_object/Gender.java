package ca.ulaval.glo4003.repul.account.domain.customer.value_object;

import java.util.Arrays;

public enum Gender {
    ABINARY("abinary"),
    AGENDER("agender"),
    AMBIGENDER("ambigender"),
    ANDROGYNE("androgyne"),
    ANDROGYNOUS("androgynous"),
    APORAGENDER("aporagender"),
    AUTIGENDER("autigender"),
    BAKLA("bakla"),
    BIGENDER("bigender"),
    BINARY("binary"),
    BISSU("bissu"),
    BUTCH("butch"),
    CALABAI("calabai"),
    CALALAI("calalai"),
    CIS("cis"),
    CISGENDER("cisgender"),
    CIS_FEMALE("cis_female"),
    CIS_MALE("cis_male"),
    CIS_MAN("cis_man"),
    CIS_WOMAN("cis_woman"),
    DEMI_BOY("demi_boy"),
    DEMIFLUX("demiflux"),
    DEMIGENDER("demigender"),
    DEMI_GIRL("demi_girl"),
    DEMI_GUY("demi_guy"),
    DEMI_MAN("demi_man"),
    DEMI_WOMAN("demi_woman"),
    DUAL_GENDER("dual_gender"),
    FEMALE("female"),
    FEMALE_TO_MALE("female_to_male"),
    FEMME("femme"),
    FTM("ftm"),
    GENDER_BENDER("gender_bender"),
    GENDER_DIVERSE("gender_diverse"),
    GENDER_GIFTED("gender_gifted"),
    GENDERFAE("genderfae"),
    GENDERFLUID("genderfluid"),
    GENDERFLUX("genderflux"),
    GENDERFUCK("genderfuck"),
    GENDERLESS("genderless"),
    GENDER_NONCONFORMING("gender_nonconforming"),
    GENDERQUEER("genderqueer"),
    GENDER_QUESTIONING("gender_questioning"),
    GENDER_VARIANT("gender_variant"),
    GRAYGENDER("graygender"),
    HIJRA("hijra"),
    INTERGENDER("intergender"),
    INTERSEX("intersex"),
    KATHOEY("kathoey"),
    MAHU("mahu"),
    MALE("male"),
    MAN("man"),
    MAVERIQUE("maverique"),
    META_GENDER("meta_gender"),
    MTF("mtf"),
    MULTIGENDER("multigender"),
    MUXE("muxe"),
    NEITHER("neither"),
    NEUROGENDER("neurogender"),
    NEUTROIS("neutrois"),
    NON_BINARY("non_binary"),
    NON_BINARY_TRANSGENDER("non_binary_transgender"),
    OMNIGENDER("omnigender"),
    OTHER("other"),
    PANGENDER("pangender"),
    POLYGENDER("polygender"),
    SEKHET("sekhet"),
    TRANS("trans"),
    TRANS_FEMALE("trans_female"),
    TRANS_MALE("trans_male"),
    TRANS_MAN("trans_man"),
    TRANS_PERSON("trans_person"),
    TRANS_WOMAN("trans_woman"),
    TRANSGENDER("transgender"),
    TRANSGENDER_FEMALE("transgender_female"),
    TRANSGENDER_MALE("transgender_male"),
    TRANSGENDER_MAN("transgender_man"),
    TRANSGENDER_PERSON("transgender_person"),
    TRANSGENDER_WOMAN("transgender_woman"),
    TRANSFEMININE("transfeminine"),
    TRANSMASCULINE("transmasculine"),
    TRANSSEXUAL("transsexual"),
    TRANSSEXUAL_FEMALE("transsexual_female"),
    TRANSSEXUAL_MALE("transsexual_male"),
    TRANSSEXUAL_MAN("transsexual_man"),
    TRANSSEXUAL_PERSON("transsexual_person"),
    TRANSSEXUAL_WOMAN("transsexual_woman"),
    TRAVESTI("travesti"),
    TRIGENDER("trigender"),
    TUMTUM("tumtum"),
    TWO_SPIRIT("two_spirit"),
    VAKASALEWALEWA("vakasalewalewa"),
    WARIA("waria"),
    WINKTE("winkte"),
    WOMAN("woman"),
    X("x"),
    X_GENDER("x_gender"),
    X_JENDA("x_jenda"),
    XENOGENDER("xenogender");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    public static Gender fromString(String input) {
        return Arrays
            .stream(values())
            .filter(gender -> gender.value.equalsIgnoreCase(input))
            .findFirst()
            .orElse(Gender.OTHER);
    }

    @Override
    public String toString() {
        return value;
    }
}
