import React from 'react'

const About = () => {
  return (
    <div name='about' className='w-full my-32'>
        <div className='max-w-[1240px] mx-auto'>
            <div className='text-center'>
                <h2 className='text-5xl font-bold'>Trusted by dotors across the world</h2>
                <p className='text-3xl py-6 text-gray-500'>The one and only system created specially for medical industry. Join the community and never worry about management again!</p>
            </div>

            <div className='grid md:grid-cols-3 gap-1 px-2 text-center'>
                <div className='border py-8 rounded-xl shadow-xl hover:scale-105 duration-300' >
                    <p className='text-6xl font-bold text-blue-700'>100%</p>
                    <p className='text-gray-400 mt-2'>Satisfaction</p>
                </div>
                <div className='border py-8 rounded-xl shadow-xl hover:scale-105 duration-300' >
                    <p className='text-6xl font-bold text-blue-700'>#1</p>
                    <p className='text-gray-400 mt-2'>In the industry</p>
                </div>
                <div  className='border py-8 rounded-xl shadow-xl hover:scale-105 duration-300' >
                    <p className='text-6xl font-bold text-blue-700'>24/7</p>
                    <p className='text-gray-400 mt-2'>Workflow</p>
                </div>
            </div>
        </div>
    </div>
  )
}

export default About