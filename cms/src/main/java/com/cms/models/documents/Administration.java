package com.cms.models.documents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Administration {
    private String president; // id
    private String vicePresident; // id
    private String accountant; // id
}
