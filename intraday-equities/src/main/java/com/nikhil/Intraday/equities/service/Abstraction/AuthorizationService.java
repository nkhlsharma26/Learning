package com.nikhil.Intraday.equities.service.Abstraction;

import com.nikhil.Intraday.equities.modal.UserDetails;

public interface AuthorizationService {
    String authorizeUser(UserDetails userDetails);
}
