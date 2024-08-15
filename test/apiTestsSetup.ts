/*
 * Copyright (c) 2014-2024 Bjoern Kimminich & the OWASP Juice Shop contributors.
 * SPDX-License-Identifier: MIT
 */

import server = require('./../server')

// eslint-disable-next-line no-async-promise-executor,@typescript-eslint/no-misused-promises
export = async () => {
  // eslint-disable-next-line no-async-promise-executor,@typescript-eslint/no-misused-promises
  await new Promise<void>(async (resolve, reject) => {
    await server.start((err?: Error) => {
      if (err) {
        reject(err)
      }
      resolve()
    })
  }
  )
}
