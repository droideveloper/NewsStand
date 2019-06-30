//
//  NewsModel.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import MVICocoa

public struct NewsModel: Model {

  public static let empty = NewsModel(state: .idle, data: [], totalResult: 0, changeState: .empty)

  public var state: SyncState
  public var data: [News]
	public var totalResult: Int?
	public var changeState: News = .empty

  public func copy(state: SyncState? = nil, data: [News]? = nil) -> NewsModel {
    return NewsModel(state: state ?? self.state, data: data ?? self.data, totalResult: self.totalResult, changeState: self.changeState)
  }
	
	public func copy(state: SyncState? = nil, data: [News]? = nil, totalResult: Int? = nil) -> NewsModel {
		return NewsModel(state: state ?? self.state, data: data ?? self.data, totalResult: totalResult ?? self.totalResult, changeState: self.changeState)
	}
	
	public func copy(state: SyncState? = nil, data: [News]? = nil, totalResult: Int? = nil, changeState: News? = nil) -> NewsModel {
		return NewsModel(state: state ?? self.state, data: data ?? self.data, totalResult: totalResult ?? self.totalResult, changeState: changeState ?? self.changeState)
	}
}
