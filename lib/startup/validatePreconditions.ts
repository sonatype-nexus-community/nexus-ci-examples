/*
 * Copyright (c) 2014-2024 Bjoern Kimminich & the OWASP Juice Shop contributors.
 * SPDX-License-Identifier: MIT
 */

import pjson from '../../package.json'
import config from 'config'
import logger from '../logger'
import path from 'path'
import colors from 'colors/safe'
import { access } from 'fs/promises'
import process from 'process'
import semver from 'semver'
import portscanner from 'portscanner'
// @ts-expect-error FIXME due to non-existing type definitions for check-internet-connected
import checkInternetConnected from 'check-internet-connected'

const domainDependencies = {
  'https://www.alchemy.com/': ['"Mint the Honeypot" challenge', '"Wallet Depletion" challenge']
}

const validatePreconditions = async ({ exitOnFailure = true } = {}) => {
  let success = true
  success = checkIfRunningOnSupportedNodeVersion(process.version) && success
  success = checkIfRunningOnSupportedOS(process.platform) && success
  success = checkIfRunningOnSupportedCPU(process.arch) && success

  const asyncConditions = (await Promise.all([
    checkIfRequiredFileExists('build/server.js'),
    checkIfRequiredFileExists('frontend/dist/frontend/index.html'),
    checkIfRequiredFileExists('frontend/dist/frontend/styles.css'),
    checkIfRequiredFileExists('frontend/dist/frontend/main.js'),
    checkIfRequiredFileExists('frontend/dist/frontend/polyfills.js'),
    checkIfRequiredFileExists('frontend/dist/frontend/runtime.js'),
    checkIfRequiredFileExists('frontend/dist/frontend/vendor.js'),
    checkIfPortIsAvailable(process.env.PORT ?? config.get<number>('server.port')),
    checkIfDomainReachable('https://www.alchemy.com/')
  ])).every(condition => condition)

  if ((!success || !asyncConditions) && exitOnFailure) {
    logger.error(colors.red('Exiting due to unsatisfied precondition!'))
    process.exit(1)
  }
  return success
}

export const checkIfRunningOnSupportedNodeVersion = (runningVersion: string) => {
  const supportedVersion = pjson.engines.node
  const effectiveVersionRange = semver.validRange(supportedVersion)
  if (!effectiveVersionRange) {
    logger.warn(`Invalid Node.js version range ${colors.bold(supportedVersion)} in package.json (${colors.red('NOT OK')})`)
    return false
  }
  if (!semver.satisfies(runningVersion, effectiveVersionRange)) {
    logger.warn(`Detected Node version ${colors.bold(runningVersion)} is not in the supported version range of ${supportedVersion} (${colors.red('NOT OK')})`)
    return false
  }
  logger.info(`Detected Node.js version ${colors.bold(runningVersion)} (${colors.green('OK')})`)
  return true
}

export const checkIfRunningOnSupportedOS = (runningOS: string) => {
  const supportedOS = pjson.os
  if (!supportedOS.includes(runningOS)) {
    logger.warn(`Detected OS ${colors.bold(runningOS)} is not in the list of supported platforms ${supportedOS.toString()} (${colors.red('NOT OK')})`)
    return false
  }
  logger.info(`Detected OS ${colors.bold(runningOS)} (${colors.green('OK')})`)
  return true
}

export const checkIfRunningOnSupportedCPU = (runningArch: string) => {
  const supportedArch = pjson.cpu
  if (!supportedArch.includes(runningArch)) {
    logger.warn(`Detected CPU ${colors.bold(runningArch)} is not in the list of supported architectures ${supportedArch.toString()} (${colors.red('NOT OK')})`)
    return false
  }
  logger.info(`Detected CPU ${colors.bold(runningArch)} (${colors.green('OK')})`)
  return true
}

export const checkIfDomainReachable = async (domain: string) => {
  return checkInternetConnected({ domain })
    .then(() => {
      logger.info(`Domain ${colors.bold(domain)} is reachable (${colors.green('OK')})`)
      return true
    })
    .catch(() => {
      logger.warn(`Domain ${colors.bold(domain)} is not reachable (${colors.yellow('NOT OK')} in a future major release)`)
      // @ts-expect-error FIXME Type problem by accessing key via variable
      domainDependencies[domain].forEach((dependency: string) => {
        logger.warn(`${colors.italic(dependency)} will not work as intended without access to ${colors.bold(domain)}`)
      })
      return true // TODO Consider switching to "false" with breaking release v16.0.0 or later
    })
}

export const checkIfPortIsAvailable = async (port: number | string) => {
  const portNumber = parseInt(port.toString())
  return await new Promise((resolve, reject) => {
    portscanner.checkPortStatus(portNumber, function (error: unknown, status: string) {
      if (error) {
        reject(error)
      } else {
        if (status === 'open') {
          logger.warn(`Port ${colors.bold(port.toString())} is in use (${colors.red('NOT OK')})`)
          resolve(false)
        } else {
          logger.info(`Port ${colors.bold(port.toString())} is available (${colors.green('OK')})`)
          resolve(true)
        }
      }
    })
  })
}

export const checkIfRequiredFileExists = async (pathRelativeToProjectRoot: string) => {
  const fileName = pathRelativeToProjectRoot.substr(pathRelativeToProjectRoot.lastIndexOf('/') + 1)

  return await access(path.resolve(pathRelativeToProjectRoot)).then(() => {
    logger.info(`Required file ${colors.bold(fileName)} is present (${colors.green('OK')})`)
    return true
  }).catch(() => {
    logger.warn(`Required file ${colors.bold(fileName)} is missing (${colors.red('NOT OK')})`)
    return false
  })
}

export default validatePreconditions
