//
//  SelectSourceEvent.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import MVICocoa
import Swinject

class SelectSourceEvent: Event {
	
	public let source: Source
	
	init(source: Source) {
		self.source = source
	}
}
