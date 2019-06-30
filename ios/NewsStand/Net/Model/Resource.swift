//
//  Resource.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation

public enum Resource<T> {
	case success(T?, Int?)
	case failure(String?, String?)
}
