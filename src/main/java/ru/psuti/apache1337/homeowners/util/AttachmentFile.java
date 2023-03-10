package ru.psuti.apache1337.homeowners.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentFile {

    private String fileName;
    private String url;

}
