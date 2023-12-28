package com.enspd.books.admin.types;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Type de livre n'on trouvé")
public class TypesNotFoundRestException extends Exception {

}
