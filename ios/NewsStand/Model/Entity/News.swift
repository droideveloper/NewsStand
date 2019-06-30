//
//  News.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation

public struct News: Codable, Equatable {

  public static let empty = News()

	public var url: String? = nil
	public var author: String? = nil
	public var title: String? = nil
	public var description: String? = nil
	public var urlToImage: String? = nil
	public var publishedAt: String? = nil // we will try to parse this later
	public var content: String? = nil
	
	public var hasBookmark: Bool = false
	
	enum CodingKeys: String, CodingKey {
		case url
		case author
		case title
		case description
		case urlToImage
		case publishedAt
		case content
	}

  public static func == (lhs: News, rhs: News) -> Bool {
    return lhs.url == rhs.url
  }  
}
