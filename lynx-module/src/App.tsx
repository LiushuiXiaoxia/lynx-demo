import { useCallback, useEffect, useState } from '@lynx-js/react'

import './App.css'
import arrow from './assets/arrow.png'
import lynxLogo from './assets/lynx-logo.png'
import reactLynxLogo from './assets/react-logo.png'

export function App(props: {
  onMounted?: () => void
}) {
  const [alterLogo, setAlterLogo] = useState(false)

  useEffect(() => {
    console.info('Hello, ReactLynx')
    props.onMounted?.()
  }, [])

  const onTap = useCallback(() => {
    'background only'
    setAlterLogo(!alterLogo)
  }, [alterLogo])

  return (
    <view>
      <view className='Background' />
      <view className='App'>
        <view className='Banner'>
          <image src={reactLynxLogo} className='Logo--react' />1
          <image src={lynxLogo} className='Logo--lynx' />}
          <text className='Title'>React</text>
          <text className='Subtitle'>on Lynx</text>
          <text className='Subtitle'>by Leon</text>
        </view>
        <view className='Content'>
          <image src={arrow} className='Arrow' />
          <text className='Description'>Tap the logo and have fun!</text>
          <text className='Hint'>
            Edit<text
              style={{
                fontStyle: 'italic',
                color: 'rgba(255, 255, 255, 0.85)',
              }}
            >
              {' src/App.tsx '}
            </text>
            to see updates!
          </text>
        </view>
        <view style={{ flex: 1 }}></view>
      </view>
    </view>
  )
}
