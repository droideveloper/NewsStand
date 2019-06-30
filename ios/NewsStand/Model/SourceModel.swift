//
//  SourceModel.swift
//  NewsStand
//
//  Created by Fatih Şen on 29.06.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import MVICocoa

public struct SourceModel: Model {

  public static let empty = SourceModel(state: .idle, data: [])

  public var state: SyncState
  public var data: [Source]

  public func copy(state: SyncState? = nil, data: [Source]? = nil) -> SourceModel {
    return SourceModel(state: state ?? self.state, data: data ?? self.data)
  }
}
