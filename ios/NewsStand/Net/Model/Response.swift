//
//  Response.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation

public class Response<T>: Codable, CustomStringConvertible where T: Codable {
	
	public var sources: T? = nil
	public var articles: T? = nil
	public var status: String? = nil
	public var totalResults: Int? = nil
	public var code: String? = nil
	public var message: String? = nil
	
	public var description: String {
		return "status: \(status ?? ""), code: \(code ?? ""), message: \(message ?? "")"
	}
}
