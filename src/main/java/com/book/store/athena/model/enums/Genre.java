package com.book.store.athena.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Genre {

    FANTASY,

    DARK_FANTASY,

    ACTION_ADVENTURE,

    ROMANCE,

    SCI_FI,

    DRAMA,

    SLICE_OF_LIFE,

    COMEDY,

    MYSTERY,

    THRILLER_SUSPENSE,

    GRAPHIC_NOVEL,

    HUMOR,

    SOCIAL_SCIENCE,

    TECHNOLOGY_SCIENCE,

    HORROR

}
