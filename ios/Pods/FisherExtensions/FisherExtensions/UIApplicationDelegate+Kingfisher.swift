//
//  UIApplicationDelegate+Kingfisher.swift
//  FisherExtensions
//
//  Created by Fatih Şen on 28.02.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import Kingfisher

extension UIApplicationDelegate {
	
	public func kingfisherCacheDefaults() {
		let cache = ImageCache.default // get the default one always
		
		cache.memoryStorage.config.totalCostLimit = defaultMemoryCacheSize
		cache.memoryStorage.config.expiration = .never // will wipe when cache is invalidated from server side
		
		cache.diskStorage.config.sizeLimit = UInt(defaultDiskCacheSize)
		cache.diskStorage.config.expiration = .never // will wipe when cache is invalidated from server
	}
}

public let defaultMemoryCacheSize = 128 * 1024 * 1024 // 128 MB

public let defaultDiskCacheSize = 512 * 1024 * 1024 // 512 MB
