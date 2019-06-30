//
//  Source.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation

public struct Source: Codable, Equatable {

  public static let empty = Source()

	var id: String? = nil
	var name: String? = nil
	var description: String? = nil
	var category: String? = nil
	var langauge: String? = nil
	var country: String? = nil
	
  public func copy() -> Source {
    return self
  }

  public static func == (lhs: Source, rhs: Source) -> Bool {
    return lhs.id == rhs.id
  }  
}
