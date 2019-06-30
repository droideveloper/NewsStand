//
//  SelectNewsEvent.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import MVICocoa
import Swinject

class SelectNewsEvent: Event {
	
	public let news: News
	
	init(news: News) {
		self.news = news
	}
}
